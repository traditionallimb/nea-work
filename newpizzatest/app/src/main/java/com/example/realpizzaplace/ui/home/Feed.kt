package com.example.realpizzaplace.ui.home

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.realpizzaplace.model.Filter
import com.example.realpizzaplace.model.PizzaCollection
import com.example.realpizzaplace.model.PizzaRepo
import com.example.realpizzaplace.ui.components.FilterBar
import com.example.realpizzaplace.ui.components.RealPizzaPlaceDivider
import com.example.realpizzaplace.ui.components.RealPizzaPlaceScaffold
import com.example.realpizzaplace.ui.components.RealPizzaPlaceSurface
import com.example.realpizzaplace.ui.components.PizzaCollection
import com.example.realpizzaplace.ui.theme.RealPizzaPlaceTheme

@Composable
fun Feed(
    onPizzaClick: (Long) -> Unit,
    onNavigateToRoute: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    // grabs pizza from the pizzarepo class
    val pizzaCollections = remember { PizzaRepo.getPizzas() }
    val filters = remember { PizzaRepo.getFilters() }
    RealPizzaPlaceScaffold(
        bottomBar = {
            RealPizzaPlaceBottomBar(
                tabs = HomeSections.values(),
                currentRoute = HomeSections.FEED.route,
                navigateToRoute = onNavigateToRoute
            )
        },
        modifier = modifier
    ) { paddingValues ->
        Feed(
            pizzaCollections,
            filters,
            onPizzaClick,
            Modifier.padding(paddingValues)
        )
    }
}

@Composable
private fun Feed(
    pizzaCollections: List<PizzaCollection>,
    filters: List<Filter>,
    onPizzaClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    RealPizzaPlaceSurface(modifier = modifier.fillMaxSize()) {
        Box {
            PizzaCollectionList(pizzaCollections, filters, onPizzaClick)
            DestinationBar()
        }
    }
}

@Composable
private fun PizzaCollectionList(
    pizzaCollections: List<PizzaCollection>,
    filters: List<Filter>,
    onPizzaClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    var filtersVisible by rememberSaveable { mutableStateOf(false) }
    Box(modifier) {
        LazyColumn {

            item {
                Spacer(
                    Modifier.windowInsetsTopHeight(
                        WindowInsets.statusBars.add(WindowInsets(top = 56.dp))
                    )
                )
                FilterBar(filters, onShowFilters = { filtersVisible = true })
            }
            itemsIndexed(pizzaCollections) { index, pizzaCollection ->
                if (index > 0) {
                    RealPizzaPlaceDivider(thickness = 2.dp)
                }

                PizzaCollection(
                    pizzaCollection = pizzaCollection,
                    onPizzaClick = onPizzaClick,
                    index = index
                )
            }
        }
    }
    AnimatedVisibility(
        visible = filtersVisible,
        enter = slideInVertically() + expandVertically(
            expandFrom = Alignment.Top
        ) + fadeIn(initialAlpha = 0.3f),
        exit = slideOutVertically() + shrinkVertically() + fadeOut()
    ) {
        FilterScreen(
            onDismiss = { filtersVisible = false }
        )
    }
}

@Preview("default")
@Preview("dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("large font", fontScale = 2f)
@Composable
fun HomePreview() {
    RealPizzaPlaceTheme {
        Feed(onPizzaClick = { }, onNavigateToRoute = { })
    }
}
