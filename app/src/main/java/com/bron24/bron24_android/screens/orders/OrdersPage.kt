package com.bron24.bron24_android.screens.orders

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bron24.bron24_android.domain.entity.order.Order
import androidx.compose.foundation.lazy.items

@Composable
fun OrdersPage(viewModel: OrdersViewModel = hiltViewModel()) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Upcoming", "History")
    val orders by viewModel.orders.collectAsState()

    Column {
        TabRow(selectedTabIndex = selectedTab) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title) }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        if (selectedTab == 0) {
//            OrdersList(orders = orders.filter { it.status == "In process" })
        } else {
//            OrdersList(orders = orders.filter { it.status == "Completed" })
        }
    }
}

//@Composable
//fun OrdersList(orders: List<Order>) {
//    LazyColumn {
//        items(orders) { order ->
//            OrderCard(order = order)
//        }
//    }
//}