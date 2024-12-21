package com.bron24.bron24_android.screens.menu_pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.bron24.bron24_android.screens.main.theme.White
import com.bron24.bron24_android.screens.menu_pages.home_page.HomePage
import com.bron24.bron24_android.screens.menu_pages.map_page.YandexMapPage
import com.bron24.bron24_android.screens.main.Screen.OrdersPage
import com.bron24.bron24_android.screens.menu_pages.orders_page.OrdersPage
import com.bron24.bron24_android.screens.menu_pages.profile_page.ProfilePage

class MenuScreen:Screen {
    @Composable
    override fun Content() {

    }
}

@Composable
fun MenuScreenContent(){
    TabNavigator(tab = HomePage){
        Scaffold (
            content = {
                Box(modifier = Modifier.padding(it)) {
                    CurrentScreen()
                }
            },
            bottomBar = {
                NavigationBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .padding(horizontal = 6.dp),
                    containerColor = White
                ) {
                    ItemBottomTab(tab = HomePage)
                    ItemBottomTab(tab = YandexMapPage)
                    ItemBottomTab(tab = OrdersPage)
                    ItemBottomTab(tab = ProfilePage)
                }
            }
        )
    }
}

@Composable
fun RowScope.ItemBottomTab(
    tab: Tab
){

}