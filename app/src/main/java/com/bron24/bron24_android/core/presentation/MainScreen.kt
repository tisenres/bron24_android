package com.bron24.bron24_android.core.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bron24.bron24_android.features.language.presentation.components.LanguageSelectionScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Surface(color = MaterialTheme.colorScheme.background) {
        NavHost(navController = navController, startDestination = "languageSelection") {
            composable("languageSelection") {
                LanguageSelectionScreen(
                    viewModel = hiltViewModel(),
                    onNavigateToLocationRequest = {
                        navController.navigate("nextScreen") {
                            popUpTo("languageSelection") { inclusive = true }
                        }
                    }
                )
            }
//            composable("locationRequest") {
//                LocationRequestScreen(
//                    onAllowClick = {
//                        navController.navigate("nextScreen") {
//                            popUpTo("locationRequest") { inclusive = true }
//                        }
//                    },
//                    onDenyClick = {
//                        navController.navigate("nextScreen") {
//                            popUpTo("locationRequest") { inclusive = true }
//                        }
//                    }
//                )
//            }
            composable("nextScreen") {
                // Content for the next screen
                Surface(color = MaterialTheme.colorScheme.background) {
                    Text("Next Screen Content")
                }
            }
        }
    }
}