package com.bron24.bron24_android.screens.menu_pages.orders_page

import com.bron24.bron24_android.data.network.dto.orders.OrderDto
import com.bron24.bron24_android.navigator.AppNavigator
import javax.inject.Inject

class OrdersPageDirection @Inject constructor(
    private val appNavigator: AppNavigator
):OrdersPageContract.Direction {
    override suspend fun moveToInfo(orderId: Long) {

    }
}