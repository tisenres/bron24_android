package com.bron24.bron24_android.screens.menu_pages.screen_menu

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.bron24.bron24_android.screens.main.theme.GrayLighter
import com.bron24.bron24_android.screens.main.theme.Success
import com.bron24.bron24_android.screens.main.theme.White
import com.bron24.bron24_android.screens.menu_pages.home_page.HomePage
import com.bron24.bron24_android.screens.menu_pages.map_page.YandexMapPage
import com.bron24.bron24_android.screens.menu_pages.orders_page.OrdersPage
import com.bron24.bron24_android.screens.menu_pages.profile_page.ProfilePage

data class MenuScreen(val tab: String?=null):Screen {
    val current = when(tab){
        "M"->YandexMapPage
        "O"->OrdersPage
        "P"->ProfilePage
        else ->null
    }
    @Composable
    override fun Content() {
        MenuScreenContent(current)
    }
}

@Composable
fun MenuScreenContent(
    tab: Tab? = null
){
    TabNavigator(tab = tab?:HomePage){
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
                        .height(64.dp),
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

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun RowScope.ItemBottomTab(
    tab: Tab?
){
    val tabNavigator = LocalTabNavigator.current
    val check = tab==tabNavigator.current
    if(tab!=null){
        Column(
            modifier = Modifier
                .height(72.dp)
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(onClick = { tabNavigator.current = tab },
                shape = CircleShape,
                modifier = Modifier.size(72.dp),
                contentPadding = PaddingValues(0.dp), colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painter = tab.options.icon!!,
                        contentDescription = "",
                        tint = if (check) Success else GrayLighter,
                        modifier = Modifier.size(28.dp),
                    )
                    if (check){
                        Text(
                            text = tab.options.title,
                            fontSize = 12.sp,
                            color = Success,
                            fontWeight = FontWeight.Normal,
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}