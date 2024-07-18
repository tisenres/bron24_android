//package com.bron24.bron24_android.features.venuelisting.presentation
//
//import android.annotation.SuppressLint
//import androidx.compose.foundation.layout.Column
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.rememberNavController
//import com.bron24.bron24_android.core.presentation.components.BottomNavigationBar
//
//@Composable
//fun HomeScreen() {
//    val navController = rememberNavController()
//    Scaffold(
//        bottomBar = {
//            BottomNavigationBar(navController)
//        }
//    ) {
//        NavHost(navController = navController, startDestination = "homePage") {
//            composable("homePage") {
//                HomePage()
//            }
//            composable("cartPage") {
//                CartPage()
//            }
//            composable("profilePage") {
//                ProfilePage()
//            }
//        }
//    }
//}
//
//data class BottomNavItem(val title: String, val route: String, val icon: ImageVector)
//
//
