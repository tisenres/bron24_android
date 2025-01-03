package com.bron24.bron24_android.screens.menu_pages.orders_page

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.bron24.bron24_android.R
import com.bron24.bron24_android.components.items.OrderCard
import com.bron24.bron24_android.components.items.OrdersTabRow
import com.bron24.bron24_android.components.items.OrdersType
import com.bron24.bron24_android.domain.entity.order.Order
import com.bron24.bron24_android.screens.main.theme.GrayLight
import com.bron24.bron24_android.screens.main.theme.Purple
import com.bron24.bron24_android.screens.main.theme.White
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily
import com.bron24.bron24_android.screens.orderdetails.OrderDetailsPage
import org.orbitmvi.orbit.compose.collectAsState


object OrdersPage : Tab {
    private fun readResolve(): Any = OrdersPage
    override val options: TabOptions
        @Composable
        get() {
            val icon =
                rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.baseline_wallet_24))
            return TabOptions(
                index = 2u,
                title = "Orders",
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


@Composable
fun OrdersPageContent(
    state: State<OrdersPageContract.UIState>,
    intent: (OrdersPageContract.Intent) -> Unit
) {

    var selectedOption by rememberSaveable { mutableStateOf(OrdersType.UPCOMING) }

    val upcomingListState = rememberLazyListState()
    val historyListState = rememberLazyListState()

//    val selectedOrders by remember {
//        derivedStateOf {
//            when (selectedOption) {
//                OrdersType.UPCOMING -> upcomingOrders
//                OrdersType.HISTORY -> historyOrders
//            }
//        }
//    }

    val selectedListState by remember {
        derivedStateOf {
            when (selectedOption) {
                OrdersType.UPCOMING -> upcomingListState
                OrdersType.HISTORY -> historyListState
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
            "Orders",
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

        Spacer(modifier = Modifier.height(16.dp))
        OrdersList(orders = emptyList(), state = rememberLazyListState()) {

        }
        if (state.value.isLoading) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(40.dp)
                )
            }
        }
        if (state.value.itemData.isEmpty()) {
            EmptyOrdersList(onButtonClick = {
                }
            )
        }
        OrdersList(
            orders = state.value.itemData,
            state = selectedListState,
            onClick = { order ->
                intent.invoke(OrdersPageContract.Intent.ClickItemOrder(order.id))
            }
        )
    }
}

@Composable
fun OrdersList(
    orders: List<Order>,
    state: LazyListState,
    onClick: (order: Order) -> Unit
) {
    LazyColumn(state = state, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        items(orders) { order ->
            OrderCard(order = order, modifier = Modifier, onClick = { onClick(order) })
        }
    }
}

@Composable
fun EmptyOrdersList(onButtonClick: () -> Unit = {}) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.align(Alignment.Center),
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
                text = "No orders yet",
                color = Purple,
                fontSize = 24.sp,
                fontFamily = gilroyFontFamily,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Browse our stadiums and start booking.",
                color = GrayLight,
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
                    "Start Booking",
                    fontSize = 16.sp,
                    fontFamily = gilroyFontFamily,
                    color = White
                )
            }
        }
    }
}
