package com.example.realpizzaplace.model

import androidx.compose.runtime.Immutable

@Immutable
data class PizzaCollection(
    val id: Long,
    val name: String,
    val pizzas: List<Pizza>,
    val type: CollectionType = CollectionType.Normal
)

enum class CollectionType { Normal, Highlight }

/**
 * A fake repo
 */
object PizzaRepo {
    fun getPizzas(): List<PizzaCollection> = pizzaCollections
    fun getPizza(pizzaId: Long) = pizzas.find { it.id == pizzaId }!!
    fun getRelated(@Suppress("UNUSED_PARAMETER") pizzaId: Long) = related
    fun getInspiredByCart() = inspiredByCart
    fun getFilters() = filters
    fun getPriceFilters() = priceFilters
    fun getCart() = cart
    fun getSortFilters() = sortFilters
    fun getCategoryFilters() = categoryFilters
    fun getSortDefault() = sortDefault
    fun getLifeStyleFilters() = lifeStyleFilters
}

/**
 * Static data
 */

private val tastyTreats = PizzaCollection(
    id = 1L,
    name = "Our picks",
    type = CollectionType.Highlight,
    pizzas = pizzas.subList(0, 13)
)

private val popular = PizzaCollection(
    id = 2L,
    name = "Popular at Real Pizza Place",
    pizzas = pizzas.subList(14, 19)
)

private val wfhFavs = tastyTreats.copy(
    id = 3L,
    name = "WFH favourites"
)

private val newlyAdded = popular.copy(
    id = 4L,
    name = "Newly Added"
)

private val exclusive = tastyTreats.copy(
    id = 5L,
    name = "Only on Jetpizza"
)

private val also = tastyTreats.copy(
    id = 6L,
    name = "Customers also bought"
)

private val inspiredByCart = tastyTreats.copy(
    id = 7L,
    name = "Inspired by your cart"
)

private val pizzaCollections = listOf(
    tastyTreats,
    popular,
    wfhFavs,
    newlyAdded,
    exclusive
)

private val related = listOf(
    also,
    popular
)

private val cart = listOf(
    OrderLine(pizzas[4], 2),
    OrderLine(pizzas[6], 3),
    OrderLine(pizzas[8], 1)
)

@Immutable
data class OrderLine(
    val pizza: Pizza,
    val count: Int
)
