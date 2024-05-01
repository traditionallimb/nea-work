package com.example.realpizzaplace.ui.home.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.realpizzaplace.R
import com.example.realpizzaplace.model.OrderLine
import com.example.realpizzaplace.model.PizzaRepo
import com.example.realpizzaplace.model.SnackbarManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Holds the contents of the cart and allows changes to it.
 *
 * TODO: Move data to Repository so it can be displayed and changed consistently throughout the app.
 */
class CartViewModel(
    private val snackbarManager: SnackbarManager,
    pizzaRepository: PizzaRepo
) : ViewModel() {

    private val _orderLines: MutableStateFlow<List<OrderLine>> =
        MutableStateFlow(pizzaRepository.getCart())
    val orderLines: StateFlow<List<OrderLine>> get() = _orderLines

    // Logic to show errors every few requests
    private var requestCount = 0
    private fun shouldRandomlyFail(): Boolean = ++requestCount % 5 == 0

    fun increasePizzaCount(pizzaId: Long) {
        if (!shouldRandomlyFail()) {
            val currentCount = _orderLines.value.first { it.pizza.id == pizzaId }.count
            updatePizzaCount(pizzaId, currentCount + 1)
        } else {
            snackbarManager.showMessage(R.string.cart_increase_error)
        }
    }

    fun decreasePizzaCount(pizzaId: Long) {
        if (!shouldRandomlyFail()) {
            val currentCount = _orderLines.value.first { it.pizza.id == pizzaId }.count
            if (currentCount == 1) {
                // remove pizza from cart
                removePizza(pizzaId)
            } else {
                // update quantity in cart
                updatePizzaCount(pizzaId, currentCount - 1)
            }
        } else {
            snackbarManager.showMessage(R.string.cart_decrease_error)
        }
    }

    fun removePizza(pizzaId: Long) {
        _orderLines.value = _orderLines.value.filter { it.pizza.id != pizzaId }
    }

    private fun updatePizzaCount(pizzaId: Long, count: Int) {
        _orderLines.value = _orderLines.value.map {
            if (it.pizza.id == pizzaId) {
                it.copy(count = count)
            } else {
                it
            }
        }
    }

    /**
     * Factory for CartViewModel that takes SnackbarManager as a dependency
     */
    companion object {
        fun provideFactory(
            snackbarManager: SnackbarManager = SnackbarManager,
            pizzaRepository: PizzaRepo = PizzaRepo
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return CartViewModel(snackbarManager, pizzaRepository) as T
            }
        }
    }
}
