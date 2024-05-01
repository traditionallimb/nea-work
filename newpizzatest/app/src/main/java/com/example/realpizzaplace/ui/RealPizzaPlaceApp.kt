package com.example.realpizzaplace.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.realpizzaplace.ui.home.HomeSections
import com.example.realpizzaplace.ui.home.addHomeGraph
import com.example.realpizzaplace.ui.navigation.MainDestinations
import com.example.realpizzaplace.ui.navigation.rememberRealPizzaPlaceNavController
import com.example.realpizzaplace.ui.pizzadetail.PizzaDetail
import com.example.realpizzaplace.ui.theme.RealPizzaPlaceTheme

@Composable
fun RealPizzaPlaceApp() {
    RealPizzaPlaceTheme {
        val realpizzaplaceNavController = rememberRealPizzaPlaceNavController()
        NavHost(
            navController = realpizzaplaceNavController.navController,
            startDestination = MainDestinations.HOME_ROUTE
        ) {
            realpizzaplaceNavGraph(
                onSnackSelected = realpizzaplaceNavController::navigateToSnackDetail,
                upPress = realpizzaplaceNavController::upPress,
                onNavigateToRoute = realpizzaplaceNavController::navigateToBottomBarRoute
            )
        }
    }
}

private fun NavGraphBuilder.realpizzaplaceNavGraph(
    onSnackSelected: (Long, NavBackStackEntry) -> Unit,
    upPress: () -> Unit,
    onNavigateToRoute: (String) -> Unit
) {
    navigation(
        route = MainDestinations.HOME_ROUTE,
        startDestination = HomeSections.FEED.route
    ) {
        addHomeGraph(onSnackSelected, onNavigateToRoute)
    }
    composable(
        "${MainDestinations.SNACK_DETAIL_ROUTE}/{${MainDestinations.SNACK_ID_KEY}}",
        arguments = listOf(navArgument(MainDestinations.SNACK_ID_KEY) { type = NavType.LongType })
    ) { backStackEntry ->
        val arguments = requireNotNull(backStackEntry.arguments)
        val snackId = arguments.getLong(MainDestinations.SNACK_ID_KEY)
        PizzaDetail(snackId, upPress)
    }
}
