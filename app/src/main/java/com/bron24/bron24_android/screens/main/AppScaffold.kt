package com.bron24.bron24_android.screens.main

import androidx.navigation.compose.composable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.bron24.bron24_android.screens.main.components.BottomNavigationBar
import com.bron24.bron24_android.screens.home.HomePage
import com.bron24.bron24_android.screens.map.YandexMapScreen

@Composable
fun AppScaffold() {
    val nestedNavController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController = nestedNavController) }
    ) { paddingValues ->
        MainNavHost(navController = nestedNavController, modifier = Modifier.padding(paddingValues))
    }
}

@Composable
fun MainNavHost(navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.HomePage.route,
        modifier = modifier
    ) {
        composable(Screen.HomePage.route) {
            HomePage()
        }
        composable(Screen.MapPage.route) {
            MapPage()
        }
        composable(Screen.CartPage.route) {
            CartPage()
        }
        composable(Screen.ProfilePage.route) {
            ProfilePage()
        }
    }
}

@Composable
fun ProfilePage() {
    Surface(color = MaterialTheme.colorScheme.background) {
        Text("Profile Page Content")
    }
}


@Composable
fun MapPage() {
    YandexMapScreen()
}

@Composable
fun CartPage() {
    Surface(color = MaterialTheme.colorScheme.background) {
        Text("Cart Page Content")
    }
}
