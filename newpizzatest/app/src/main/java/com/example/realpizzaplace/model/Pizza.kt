package com.example.realpizzaplace.model

import androidx.compose.runtime.Immutable

@Immutable
data class Pizza(
    val id: Long,
    val name: String,
    val price: Long,
    val tagline: String = "",
)

/**
 * Static data
 */

val pizzas = listOf(
    Pizza(
        id = 1L,
        name = "Cheese and Tomato",
        tagline = "A tag line",
        price = 1000
    ),
    Pizza(
        id = 2L,
        name = "Meaty Feasty",
        tagline = "A tag line",
        price = 1000
    ),
    Pizza(
        id = 3L,
        name = "Hawiian",
        tagline = "A tag line",
        price = 1000
    ),
    Pizza(
        id = 4L,
        name = "Pepperoni",
        tagline = "A tag line",
        price = 1000
    ),
    Pizza(
        id = 5L,
        name = "Texas BBQ",
        tagline = "A tag line",
        price = 1000
    ),
    Pizza(
        id = 6L,
        name = "Veggi Supreme",
        tagline = "A tag line",
        price = 1000
    ),
    Pizza(
        id = 7L,
        name = "Chicken Feast",
        tagline = "A tag line",
        price = 1000
    ),
    Pizza(
        id = 8L,
        name = "Hot and Spicy",
        tagline = "A tag line",
        price = 1000
    ),
    Pizza(
        id = 9L,
        name = "Garlic Bread",
        tagline = "A tag line",
        price = 500
    ),
    Pizza(
        id = 10L,
        name = "Chips",
        tagline = "A tag line",
        price = 500
    ),
    Pizza(
        id = 11L,
        name = "Dough Balls",
        tagline = "A tag line",
        price = 500
    ),
    Pizza(
        id = 12L,
        name = "Potato Wedges",
        tagline = "A tag line",
        price = 500
    ),
    Pizza(
        id = 13L,
        name = "Chicken Nuggets",
        tagline = "A tag line",
        price = 500
    ),
    Pizza(
        id = 14L,
        name = "Chicken Wings",
        tagline = "A tag line",
        price = 500
    ),
    Pizza(
        id = 15L,
        name = "Chicken Strips",
        price = 500
    ),
    Pizza(
        id = 16L,
        name = "Cookies",
        price = 500
    ),
    Pizza(
        id = 17L,
        name = "Ice Cream",
        price = 500
    ),
    Pizza(
        id = 18L,
        name = "Coke",
        price = 300
    ),
    Pizza(
        id = 19L,
        name = "Water",
        price = 300
    ),
    Pizza(
        id = 20L,
        name = "Fanta",
        price = 300
    ),
    Pizza(
        id = 21L,
        name = "Dr Pepper",
        tagline = "A tag line",
        price = 300
    ),
    Pizza(
        id = 22L,
        name = "Sprite",
        tagline = "A tag line",
        price = 300
    ),
    Pizza(
        id = 23L,
        name = "Coffee",
        tagline = "A tag line",
        price = 300
    ),
    Pizza(
        id = 24L,
        name = "Tea",
        tagline = "A tag line",
        price = 300
    ),
    Pizza(
        id = 25L,
        name = "Hot Chocolate",
        tagline = "A tag line",
        price = 300
    ),
    Pizza(
        id = 26L,
        name = "Lemonade",
        tagline = "A tag line",
        price = 300
    ),
    Pizza(
        id = 27L,
        name = "Capri-Sun",
        tagline = "A tag line",
        price = 150
    ),
    Pizza(
        id = 28L,
        name = "Fruit Shoot",
        tagline = "A tag line",
        price = 150
    )
)
