package com.example.realpizzaplace.ui.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarData
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.realpizzaplace.ui.theme.RealPizzaPlaceTheme

/**
 * An alternative to [androidx.compose.material.Snackbar] utilizing
 * [com.example.realpizzaplace.ui.theme.RealPizzaPlaceColors]
 */
@Composable
fun RealPizzaPlaceSnackbar(
    snackbarData: SnackbarData,
    modifier: Modifier = Modifier,
    actionOnNewLine: Boolean = false,
    shape: Shape = MaterialTheme.shapes.small,
    backgroundColor: Color = RealPizzaPlaceTheme.colors.uiBackground,
    contentColor: Color = RealPizzaPlaceTheme.colors.textSecondary,
    actionColor: Color = RealPizzaPlaceTheme.colors.brand,
    elevation: Dp = 6.dp
) {
    Snackbar(
        snackbarData = snackbarData,
        modifier = modifier,
        actionOnNewLine = actionOnNewLine,
        shape = shape,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        actionColor = actionColor,
        elevation = elevation
    )
}
