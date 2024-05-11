package com.example.realpizzaplace.model

import androidx.compose.runtime.Immutable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

/**
 * A fake repo for searching.
 */
object SearchRepo {
    fun getCategories(): List<SearchCategoryCollection> = searchCategoryCollections
    fun getSuggestions(): List<SearchSuggestionGroup> = searchSuggestions

    suspend fun search(query: String): List<Pizza> = withContext(Dispatchers.Default) {
        delay(200L) // simulate an I/O delay
        pizzas.filter { it.name.contains(query, ignoreCase = true) }
    }
}

@Immutable
data class SearchCategoryCollection(
    val id: Long,
    val name: String,
    val categories: List<SearchCategory>
)

@Immutable
data class SearchCategory(
    val name: String,
    val imageUrl: String
)

@Immutable
data class SearchSuggestionGroup(
    val id: Long,
    val name: String,
    val suggestions: List<String>
)

/**
 * Static data
 */

private val searchCategoryCollections = listOf(
    SearchCategoryCollection(
        id = 0L,
        name = "Categories",
        categories = listOf(
            SearchCategory(
                name = "Pizzas",
                imageUrl = ""
            ),
            SearchCategory(
                name = "Sides",
                imageUrl = ""
            ),
            SearchCategory(
                name = "Desserts",
                imageUrl = ""
            ),
            SearchCategory(
                name = "Drinks",
                imageUrl = ""
            )
        )
    ),
    SearchCategoryCollection(
        id = 1L,
        name = "Lifestyles",
        categories = listOf(
            SearchCategory(
                name = "Dairy Free",
                imageUrl = ""
            ),
            SearchCategory(
                name = "Gluten Free",
                imageUrl = ""
            ),
            SearchCategory(
                name = "Nut Safe",
                imageUrl = ""
            ),
            SearchCategory(
                name = "Vegan",
                imageUrl = ""
            ),
            SearchCategory(
                name = "Vegetarian",
                imageUrl = ""
            )
        )
    )
)

private val searchSuggestions = listOf(
    SearchSuggestionGroup(
        id = 0L,
        name = "Recent searches",
        suggestions = listOf(
            "Pepperoni",
            "Fanta"
        )
    )
)
