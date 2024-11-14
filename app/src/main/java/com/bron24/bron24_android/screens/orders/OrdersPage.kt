package com.bron24.bron24_android.screens.orders

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bron24.bron24_android.domain.entity.order.Order
import com.bron24.bron24_android.domain.entity.order.OrderStatus
import com.bron24.bron24_android.screens.main.theme.gilroyFontFamily

@Composable
fun OrdersPage(viewModel: OrdersViewModel = hiltViewModel(), navController: NavHostController) {
    val orders by viewModel.orders.collectAsState()
    var selectedOption by remember { mutableStateOf(OrdersType.UPCOMING) }

    Column(
        modifier = Modifier.padding(start = 24.dp, end = 24.dp),
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
        OrdersTabRow(
            selectedOption = selectedOption,
            onClick = {
                selectedOption = it
            })

        Spacer(modifier = Modifier.height(16.dp))
        if (orders.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        } else {
            if (selectedOption == OrdersType.UPCOMING) {
                OrdersList(
                    orders = orders.filter { it.status == OrderStatus.IN_PROCESS },
                    onClick = { order ->

                    })
            } else {
                OrdersList(
                    orders = orders.filter { it.status == OrderStatus.CANCELLED },
                    onClick = { order ->

                    })
            }
        }
    }
}

@Composable
fun OrdersList(orders: List<Order>, onClick: (order: Order) -> Unit) {
    LazyColumn {
        items(orders) { order ->
            Spacer(modifier = Modifier.height(20.dp))
            OrderCard(order = order, modifier = Modifier, onClick = { onClick(order) })
        }
    }
}