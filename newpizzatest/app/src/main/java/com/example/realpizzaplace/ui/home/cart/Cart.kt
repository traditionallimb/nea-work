package com.example.realpizzaplace.ui.home.cart

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.realpizzaplace.R
import com.example.realpizzaplace.model.OrderLine
import com.example.realpizzaplace.model.PizzaCollection
import com.example.realpizzaplace.model.PizzaRepo
import com.example.realpizzaplace.ui.components.RealPizzaPlaceButton
import com.example.realpizzaplace.ui.components.RealPizzaPlaceDivider
import com.example.realpizzaplace.ui.components.RealPizzaPlaceScaffold
import com.example.realpizzaplace.ui.components.RealPizzaPlaceSnackbar
import com.example.realpizzaplace.ui.components.RealPizzaPlaceSurface
import com.example.realpizzaplace.ui.components.QuantitySelector
import com.example.realpizzaplace.ui.components.PizzaCollection
import com.example.realpizzaplace.ui.components.PizzaImage
import com.example.realpizzaplace.ui.components.rememberRealPizzaPlaceScaffoldState
import com.example.realpizzaplace.ui.home.DestinationBar
import com.example.realpizzaplace.ui.home.HomeSections
import com.example.realpizzaplace.ui.home.RealPizzaPlaceBottomBar
import com.example.realpizzaplace.ui.theme.AlphaNearOpaque
import com.example.realpizzaplace.ui.theme.RealPizzaPlaceTheme
import com.example.realpizzaplace.ui.utils.formatPrice

@Composable
fun Cart(
    onPizzaClick: (Long) -> Unit,
    onNavigateToRoute: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CartViewModel = viewModel(factory = CartViewModel.provideFactory())
) {
    val orderLines by viewModel.orderLines.collectAsStateWithLifecycle()
    val inspiredByCart = remember { PizzaRepo.getInspiredByCart() }
    val realpizzaplaceScaffoldState = rememberRealPizzaPlaceScaffoldState()
    RealPizzaPlaceScaffold(
        bottomBar = {
            RealPizzaPlaceBottomBar(
                tabs = HomeSections.entries.toTypedArray(),
                currentRoute = HomeSections.CART.route,
                navigateToRoute = onNavigateToRoute
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = it,
                modifier = Modifier.systemBarsPadding(),
                snackbar = { snackbarData -> RealPizzaPlaceSnackbar(snackbarData) }
            )
        },
        scaffoldState = realpizzaplaceScaffoldState.scaffoldState,
        modifier = modifier
    ) { paddingValues ->
        Cart(
            orderLines = orderLines,
            removePizza = viewModel::removePizza,
            increaseItemCount = viewModel::increasePizzaCount,
            decreaseItemCount = viewModel::decreasePizzaCount,
            inspiredByCart = inspiredByCart,
            onPizzaClick = onPizzaClick,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun Cart(
    orderLines: List<OrderLine>,
    removePizza: (Long) -> Unit,
    increaseItemCount: (Long) -> Unit,
    decreaseItemCount: (Long) -> Unit,
    inspiredByCart: PizzaCollection,
    onPizzaClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    RealPizzaPlaceSurface(modifier = modifier.fillMaxSize()) {
        Box {
            CartContent(
                orderLines = orderLines,
                removePizza = removePizza,
                increaseItemCount = increaseItemCount,
                decreaseItemCount = decreaseItemCount,
                inspiredByCart = inspiredByCart,
                onPizzaClick = onPizzaClick,
                modifier = Modifier.align(Alignment.TopCenter)
            )
            DestinationBar(modifier = Modifier.align(Alignment.TopCenter))
            CheckoutBar(modifier = Modifier.align(Alignment.BottomCenter))
        }
    }
}

@Composable
private fun CartContent(
    orderLines: List<OrderLine>,
    removePizza: (Long) -> Unit,
    increaseItemCount: (Long) -> Unit,
    decreaseItemCount: (Long) -> Unit,
    inspiredByCart: PizzaCollection,
    onPizzaClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val resources = LocalContext.current.resources
    val pizzaCountFormattedString = remember(orderLines.size, resources) {
        resources.getQuantityString(
            R.plurals.cart_order_count,
            orderLines.size, orderLines.size
        )
    }
    LazyColumn(modifier) {
        item {
            Spacer(
                Modifier.windowInsetsTopHeight(
                    WindowInsets.statusBars.add(WindowInsets(top = 56.dp))
                )
            )
            Text(
                text = stringResource(R.string.cart_order_header, pizzaCountFormattedString),
                style = MaterialTheme.typography.h6,
                color = RealPizzaPlaceTheme.colors.brand,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .heightIn(min = 56.dp)
                    .padding(horizontal = 24.dp, vertical = 4.dp)
                    .wrapContentHeight()
            )
        }
        items(orderLines) { orderLine ->
            SwipeDismissItem(
                background = { offsetX ->
                    /*Background color changes from light gray to red when the
                    swipe to delete with exceeds 160.dp*/
                    val backgroundColor = if (offsetX < -160.dp) {
                        RealPizzaPlaceTheme.colors.error
                    } else {
                        RealPizzaPlaceTheme.colors.uiFloated
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .background(backgroundColor),
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.Center
                    ) {
                        // Set 4.dp padding only if offset is bigger than 160.dp
                        val padding: Dp by animateDpAsState(
                            if (offsetX > -160.dp) 4.dp else 0.dp
                        )
                        Box(
                            Modifier
                                .width(offsetX * -1)
                                .padding(padding)
                        ) {
                            // Height equals to width removing padding
                            val height = (offsetX + 8.dp) * -1
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(height)
                                    .align(Alignment.Center),
                                shape = CircleShape,
                                color = RealPizzaPlaceTheme.colors.error
                            ) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    // Icon must be visible while in this width range
                                    if (offsetX < -40.dp && offsetX > -152.dp) {
                                        // Icon alpha decreases as it is about to disappear
                                        val iconAlpha: Float by animateFloatAsState(
                                            if (offsetX < -120.dp) 0.5f else 1f
                                        )

                                        Icon(
                                            imageVector = Icons.Filled.DeleteForever,
                                            modifier = Modifier
                                                .size(16.dp)
                                                .graphicsLayer(alpha = iconAlpha),
                                            tint = RealPizzaPlaceTheme.colors.uiBackground,
                                            contentDescription = null,
                                        )
                                    }
                                    /*Text opacity increases as the text is supposed to appear in
                                    the screen*/
                                    val textAlpha by animateFloatAsState(
                                        if (offsetX > -144.dp) 0.5f else 1f
                                    )
                                    if (offsetX < -120.dp) {
                                        Text(
                                            text = stringResource(id = R.string.remove_item),
                                            style = MaterialTheme.typography.subtitle1,
                                            color = RealPizzaPlaceTheme.colors.uiBackground,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier
                                                .graphicsLayer(
                                                    alpha = textAlpha
                                                )
                                        )
                                    }
                                }
                            }
                        }
                    }
                },
            ) {
                CartItem(
                    orderLine = orderLine,
                    removePizza = removePizza,
                    increaseItemCount = increaseItemCount,
                    decreaseItemCount = decreaseItemCount,
                    onPizzaClick = onPizzaClick
                )
            }
        }
        item {
            SummaryItem(
                subtotal = orderLines.map { it.pizza.price * it.count }.sum(),
                shippingCosts = 369
            )
        }
        item {
            PizzaCollection(
                pizzaCollection = inspiredByCart,
                onPizzaClick = onPizzaClick,
                highlight = false
            )
            Spacer(Modifier.height(56.dp))
        }
    }
}

@Composable
fun CartItem(
    orderLine: OrderLine,
    removePizza: (Long) -> Unit,
    increaseItemCount: (Long) -> Unit,
    decreaseItemCount: (Long) -> Unit,
    onPizzaClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val pizza = orderLine.pizza
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onPizzaClick(pizza.id) }
            .background(RealPizzaPlaceTheme.colors.uiBackground)
            .padding(horizontal = 24.dp)

    ) {
        val (divider, image, name, tag, priceSpacer, price, remove, quantity) = createRefs()
        createVerticalChain(name, tag, priceSpacer, price, chainStyle = ChainStyle.Packed)
        PizzaImage(
            imageUrl = pizza.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .constrainAs(image) {
                    top.linkTo(parent.top, margin = 16.dp)
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                }
        )
        Text(
            text = pizza.name,
            style = MaterialTheme.typography.subtitle1,
            color = RealPizzaPlaceTheme.colors.textSecondary,
            modifier = Modifier.constrainAs(name) {
                linkTo(
                    start = image.end,
                    startMargin = 16.dp,
                    end = remove.start,
                    endMargin = 16.dp,
                    bias = 0f
                )
            }
        )
        IconButton(
            onClick = { removePizza(pizza.id) },
            modifier = Modifier
                .constrainAs(remove) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
                .padding(top = 12.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Close,
                tint = RealPizzaPlaceTheme.colors.iconSecondary,
                contentDescription = stringResource(R.string.label_remove)
            )
        }
        Text(
            text = pizza.tagline,
            style = MaterialTheme.typography.body1,
            color = RealPizzaPlaceTheme.colors.textHelp,
            modifier = Modifier.constrainAs(tag) {
                linkTo(
                    start = image.end,
                    startMargin = 16.dp,
                    end = parent.end,
                    endMargin = 16.dp,
                    bias = 0f
                )
            }
        )
        Spacer(
            Modifier
                .height(8.dp)
                .constrainAs(priceSpacer) {
                    linkTo(top = tag.bottom, bottom = price.top)
                }
        )
        Text(
            text = formatPrice(pizza.price),
            style = MaterialTheme.typography.subtitle1,
            color = RealPizzaPlaceTheme.colors.textPrimary,
            modifier = Modifier.constrainAs(price) {
                linkTo(
                    start = image.end,
                    end = quantity.start,
                    startMargin = 16.dp,
                    endMargin = 16.dp,
                    bias = 0f
                )
            }
        )
        QuantitySelector(
            count = orderLine.count,
            decreaseItemCount = { decreaseItemCount(pizza.id) },
            increaseItemCount = { increaseItemCount(pizza.id) },
            modifier = Modifier.constrainAs(quantity) {
                baseline.linkTo(price.baseline)
                end.linkTo(parent.end)
            }
        )
        RealPizzaPlaceDivider(
            Modifier.constrainAs(divider) {
                linkTo(start = parent.start, end = parent.end)
                top.linkTo(parent.bottom)
            }
        )
    }
}

@Composable
fun SummaryItem(
    subtotal: Long,
    shippingCosts: Long,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Text(
            text = stringResource(R.string.cart_summary_header),
            style = MaterialTheme.typography.h6,
            color = RealPizzaPlaceTheme.colors.brand,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .heightIn(min = 56.dp)
                .wrapContentHeight()
        )
        Row(modifier = Modifier.padding(horizontal = 24.dp)) {
            Text(
                text = stringResource(R.string.cart_subtotal_label),
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.Start)
                    .alignBy(LastBaseline)
            )
            Text(
                text = formatPrice(subtotal),
                style = MaterialTheme.typography.body1,
                modifier = Modifier.alignBy(LastBaseline)
            )
        }
        Row(modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)) {
            Text(
                text = stringResource(R.string.cart_shipping_label),
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.Start)
                    .alignBy(LastBaseline)
            )
            Text(
                text = formatPrice(shippingCosts),
                style = MaterialTheme.typography.body1,
                modifier = Modifier.alignBy(LastBaseline)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        RealPizzaPlaceDivider()
        Row(modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)) {
            Text(
                text = stringResource(R.string.cart_total_label),
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp)
                    .wrapContentWidth(Alignment.End)
                    .alignBy(LastBaseline)
            )
            Text(
                text = formatPrice(subtotal + shippingCosts),
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.alignBy(LastBaseline)
            )
        }
        RealPizzaPlaceDivider()
    }
}

@Composable
private fun CheckoutBar(modifier: Modifier = Modifier) {
    Column(
        modifier.background(
            RealPizzaPlaceTheme.colors.uiBackground.copy(alpha = AlphaNearOpaque)
        )
    ) {

        RealPizzaPlaceDivider()
        Row {
            Spacer(Modifier.weight(1f))
            RealPizzaPlaceButton(
                onClick = { /* todo */ },
                shape = RectangleShape,
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 8.dp)
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(id = R.string.cart_checkout),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Left,
                    maxLines = 1
                )
            }
        }
    }
}

@Preview("default")
@Preview("dark theme", uiMode = UI_MODE_NIGHT_YES)
@Preview("large font", fontScale = 2f)
@Composable
private fun CartPreview() {
    RealPizzaPlaceTheme {
        Cart(
            orderLines = PizzaRepo.getCart(),
            removePizza = {},
            increaseItemCount = {},
            decreaseItemCount = {},
            inspiredByCart = PizzaRepo.getInspiredByCart(),
            onPizzaClick = {}
        )
    }
}
