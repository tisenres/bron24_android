package com.bron24.bron24_android.screens.menu_pages.orders_page

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.bron24.bron24_android.R
import com.bron24.bron24_android.components.items.OrderCard
import com.bron24.bron24_android.components.items.OrdersTabRow
import com.bron24.bron24_android.components.items.OrdersType
import com.bron24.bron24_android.components.items.VenueLoadingPlaceholder
import com.bron24.bron24_android.domain.entity.order.Order
import com.bron24.bron24_android.screens.main.theme.GrayLight
import com.bron24.bron24_android.screens.main.theme.Purple
import com.bron24.bron24_android.screens.main.theme.White
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily
import com.bron24.bron24_android.screens.menu_pages.home_page.HomePage
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.orbitmvi.orbit.compose.collectAsState


object OrdersPage : Tab {
    private fun readResolve(): Any = OrdersPage
    override val options: TabOptions
        @Composable
        get() {
            val icon =
                rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.ic_wallet))
            return TabOptions(
                index = 2u,
                title = stringResource(id = R.string.orders),
                icon = icon
            )
        }

    @Composable
    override fun Content() {
        val viewModel: OrdersPageContract.ViewModel = getViewModel<OrdersPageVM>()
        remember {
            viewModel.initData()
        }
        val uiState = viewModel.collectAsState()
        OrdersPageContent(uiState, viewModel::onDispatchers)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersPageContent(
    state: State<OrdersPageContract.UIState>,
    intent: (OrdersPageContract.Intent) -> Unit
) {

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(color = White, darkIcons = true)

    val upcomingListState = rememberLazyListState()
    val historyListState = rememberLazyListState()
    var selectedOption by rememberSaveable { mutableStateOf(OrdersType.UPCOMING) }

    selectedOption = state.value.selected

    val tab = LocalTabNavigator.current
    BackHandler {
        tab.current = HomePage
    }

    val selectedListState by remember {
        derivedStateOf {
            when (selectedOption) {
                OrdersType.UPCOMING -> {
                    upcomingListState
                }

                OrdersType.HISTORY -> {
                    historyListState
                }
            }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)
            .padding(start = 24.dp, end = 24.dp),
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            stringResource(id = R.string.orders),
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Black,
            fontFamily = gilroyFontFamily
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Tab Row for switching between order types

        OrdersTabRow(
            selectedOption = selectedOption,
            onClick = {
                when (it) {
                    OrdersType.UPCOMING -> {
                        intent.invoke(OrdersPageContract.Intent.ClickUpcoming)
                    }

                    OrdersType.HISTORY -> {
                        intent.invoke(OrdersPageContract.Intent.ClickHistory)
                    }
                }
                selectedOption = it
            }
        )

        if (state.value.isLoading) {
            Box(modifier = Modifier.fillMaxSize()) {
                LazyColumn(contentPadding = PaddingValues(vertical = 24.dp)) {
                    items(5) {
                        VenueLoadingPlaceholder()
                    }
                }
            }
        }
        val pullRefreshState = rememberPullToRefreshState()

        PullToRefreshBox(
            state = pullRefreshState,
            onRefresh = {
                intent.invoke(OrdersPageContract.Intent.Refresh(state.value.selected))
            },
            isRefreshing = state.value.refresh,
            modifier = Modifier,
            indicator = {
                PullToRefreshDefaults.Indicator(
                    state = pullRefreshState,
                    isRefreshing = state.value.refresh,
                    color = MaterialTheme.colorScheme.tertiary,
                    containerColor = Color.White,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }
        ) {
            Column {
                OrdersList(
                    stateUi = state,
                    state = selectedListState,
                    clickBooking = {
                        tab.current = HomePage
                    },
                    refresh = state.value.refresh,
                    onClick = { order ->
                        intent.invoke(OrdersPageContract.Intent.ClickItemOrder(order.id))
                    }
                )
            }
        }
    }
}

@Composable
fun OrdersList(
    stateUi: State<OrdersPageContract.UIState>,
    state: LazyListState,
    clickBooking: () -> Unit,
    refresh: Boolean,
    onClick: (order: Order) -> Unit,
) {
    if (stateUi.value.selected == OrdersType.UPCOMING) {
        if (stateUi.value.inProcess.isEmpty()) {
            EmptyOrdersList {
                clickBooking.invoke()
            }
        } else {
            LazyColumn(state = state, verticalArrangement = Arrangement.spacedBy(16.dp), contentPadding = PaddingValues(vertical = 24.dp)) {
                if (stateUi.value.inProcess.isEmpty()) {
                    item {

                    }
                } else {
                    if (!refresh)
                        items(stateUi.value.inProcess) { order ->
                            OrderCard(
                                order = order, modifier = Modifier.fillMaxSize(),
                                onClick = { onClick(order) })
                        }
                    else {
                        items(5) {
                            VenueLoadingPlaceholder()
                        }
                    }
                }

            }
        }
    } else {
        if (stateUi.value.history.isEmpty()) {
            EmptyOrdersList {
                clickBooking.invoke()
            }
        } else {
            LazyColumn(state = state, verticalArrangement = Arrangement.spacedBy(16.dp), contentPadding = PaddingValues(vertical = 24.dp)) {
                if (stateUi.value.history.isEmpty()) {
                    item {

                    }
                } else {
                    if (!refresh)
                        items(stateUi.value.history) { order ->
                            OrderCard(
                                order = order, modifier = Modifier.fillMaxSize(),
                                onClick = { onClick(order) })
                        }
                    else {
                        items(5) {
                            VenueLoadingPlaceholder()
                        }
                    }
                }

            }
        }
    }
    val inProg = stateUi.value.inProcess
    val history = stateUi.value.history


}

@Composable
fun EmptyOrdersList(onButtonClick: () -> Unit = {}) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.empty_ball),
                contentDescription = "Empty",
                modifier = Modifier
                    .width(135.dp)
                    .height(114.dp)
            )
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = stringResource(id = R.string.no_orders_yet),
                color = Purple,
                fontSize = 24.sp,
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(id = R.string.info_booking),
                color = GrayLight,
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Normal
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                modifier = Modifier
                    .width(196.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(10.dp),
                onClick = onButtonClick
            ) {
                Text(
                    stringResource(id = R.string.start_booking),
                    fontSize = 16.sp,
                    fontFamily = gilroyFontFamily,
                    color = White
                )
            }
        }
    }
}
