package com.bron24.bron24_android.core.presentation

import androidx.navigation.compose.composable
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.bron24.bron24_android.core.domain.model.Screen
import com.bron24.bron24_android.features.language.presentation.LanguageSelectionScreen
import com.bron24.bron24_android.features.location.presentation.components.LocationRequestScreen

@Composable
fun NavScreen(navController: NavHostController) {
    Surface(color = MaterialTheme.colorScheme.background) {
        NavHost(navController = navController, startDestination = Screen.LanguageSelection.route) {
            composable(Screen.LanguageSelection.route) {
                LanguageSelectionScreen(
                    viewModel = hiltViewModel(),
                    onNavigateToLocationRequest = {
                        navController.navigate(Screen.LocationPermission.route) {
                            popUpTo(Screen.LanguageSelection.route) { inclusive = true }
                        }
                    }
                )
            }
//            composable(Screen.CitySelection.route) {
//                CitySelectionScreen(
//                    viewModel = hiltViewModel(),
//                    onNavigateToLocationRequest = {
//                        navController.navigate(Screen.Main.route) {
//                            popUpTo(Screen.CitySelection.route) { inclusive = true }
//                        }
//                    }
//                )
//            }
            composable(Screen.LocationPermission.route) {
                LocationRequestScreen(
                    onAllowClick = {
                        navController.navigate(Screen.Main.route) {
                            popUpTo(Screen.LocationPermission.route) { inclusive = true }
                        }
                    },
                    onDenyClick = {
                        navController.navigate(Screen.Main.route) {
                            popUpTo(Screen.LocationPermission.route) { inclusive = true }
                        }
                    },
                    viewModel = hiltViewModel()
                )
            }
            composable(Screen.Main.route) {
                AppScaffold()
            }
        }
    }
}