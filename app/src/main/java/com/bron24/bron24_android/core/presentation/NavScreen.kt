package com.bron24.bron24_android.core.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bron24.bron24_android.core.domain.model.Screen
import com.bron24.bron24_android.core.presentation.components.BottomNavigationBar
import com.bron24.bron24_android.features.cityselection.presentation.CitySelectionScreen
import com.bron24.bron24_android.features.language.presentation.LanguageSelectionScreen

@Composable
fun NavScreen() {
    val navController = rememberNavController()
    Surface(color = MaterialTheme.colorScheme.background) {
        NavHost(navController = navController, startDestination = "languageSelection") {
            composable("languageSelection") {
                LanguageSelectionScreen(
                    viewModel = hiltViewModel(),
                    onNavigateToLocationRequest = {
                        navController.navigate("citySelection") {
                            popUpTo("languageSelection") { inclusive = true }
                        }
                    }
                )
            }
            composable("citySelection") {
                CitySelectionScreen(
                    viewModel = hiltViewModel(),
                    onNavigateToLocationRequest = {
                        navController.navigate("main") {
                            popUpTo("citySelection") { inclusive = true }
                        }
                    }
                )
            }
            composable("main") {
                Scaffold(
                    bottomBar = { BottomNavigationBar(navController = navController) }
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = Screen.HomePage.route,
                        modifier = Modifier.padding(it)
                    ) {
                        composable(Screen.HomePage.route) {
                            HomePage()
                        }
                        composable(Screen.CartPage.route) {
                            CartPage()
                        }
                        composable(Screen.ProfilePage.route) {
                            ProfilePage()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HomePage() {
}

@Composable
fun CartPage() {
    Surface(color = MaterialTheme.colorScheme.background) {
        Text("Cart Page Content")
    }
}

@Composable
fun ProfilePage() {
    Surface(color = MaterialTheme.colorScheme.background) {
        Text("Profile Page Content")
    }
}
