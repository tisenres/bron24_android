package com.bron24.bron24_android.screens.booking.screens.finishbooking

import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import com.bron24.bron24_android.navigator.AppNavigator
import com.bron24.bron24_android.screens.menu_pages.orders_page.OrdersPage
import com.bron24.bron24_android.screens.menu_pages.screen_menu.MenuScreen
import javax.inject.Inject

class BookingSuccessDirection @Inject constructor(
    private val appNavigator: AppNavigator
):BookingSuccessContract.Direction {
    override suspend fun moveToOrder() {
        appNavigator.replaceAll(MenuScreen("O"))
    }

    override suspend fun moveToMenu() {
        appNavigator.replaceAll(MenuScreen())
    }
}