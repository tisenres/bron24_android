package com.bron24.bron24_android.core.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.ui.graphics.Color
import com.bron24.bron24_android.core.domain.model.Screen

@Composable
fun BottomNavigationBar(navController: NavController) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.primary,
        contentColor = Color.White
    ) {
        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry.value?.destination?.route

        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = null) },
            label = { Text("Home") },
            selected = currentRoute == Screen.HomePage.route,
            onClick = { navController.navigate(Screen.HomePage.route) }
        )

        BottomNavigationItem(
            icon = { Icon(Icons.Filled.ShoppingCart, contentDescription = null) },
            label = { Text("Cart") },
            selected = currentRoute == Screen.CartPage.route,
            onClick = { navController.navigate(Screen.CartPage.route) }
        )

        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Person, contentDescription = null) },
            label = { Text("Profile") },
            selected = currentRoute == Screen.ProfilePage.route,
            onClick = { navController.navigate(Screen.ProfilePage.route) }
        )
    }
}

@Composable
fun BottomNavigation(backgroundColor: Color, contentColor: Color, content: @Composable () -> Unit) {
    TODO("Not yet implemented")
}
