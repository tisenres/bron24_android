package com.bron24.bron24_android.core.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bron24.bron24_android.core.domain.model.Screen
import com.bron24.bron24_android.core.presentation.components.BottomNavigationBar
import com.bron24.bron24_android.features.cityselection.presentation.CitySelectionScreen
import com.bron24.bron24_android.features.language.presentation.LanguageSelectionScreen

@Composable
fun NavScreen(navController: NavHostController = rememberNavController()) {
    Surface(color = MaterialTheme.colorScheme.background) {
        NavHost(navController = navController, startDestination = Screen.LanguageSelection.route) {
            composable(Screen.LanguageSelection.route) {
                LanguageSelectionScreen(
                    viewModel = hiltViewModel(),
                    onNavigateToLocationRequest = {
                        navController.navigate(Screen.CitySelection.route) {
                            popUpTo(Screen.LanguageSelection.route) { inclusive = true }
                        }
                    }
                )
            }
            composable(Screen.CitySelection.route) {
                CitySelectionScreen(
                    viewModel = hiltViewModel(),
                    onNavigateToLocationRequest = {
                        navController.navigate(Screen.Main.route) {
                            popUpTo(Screen.CitySelection.route) { inclusive = true }
                        }
                    }
                )
            }
            composable(Screen.Main.route) {
                Scaffold(
                    bottomBar = { BottomNavigationBar(navController = navController) }
                ) { paddingValues ->
                    MainNavHost(navController = navController, modifier = Modifier.padding(paddingValues))
                }
            }
        }
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
    TODO("Not yet implemented")
}

@Composable
fun CartPage() {
    TODO("Not yet implemented")
}

@Composable
fun HomePage() {
    Surface(color = Color.White) {
        Text("Home Page Content")
    }
}
