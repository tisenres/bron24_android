package com.bron24.bron24_android.core.presentation

import android.content.Context
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bron24.bron24_android.features.language.presentation.LanguageSelectionScreen
import com.bron24.bron24_android.features.cityselection.presentation.CitySelectionScreen
import com.bron24.bron24_android.core.util.LocaleManager

@Composable
fun MainScreen() {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        val sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        val languageCode = sharedPreferences.getString("selected_language", "uz") ?: "uz"
        LocaleManager.updateLocale(context, languageCode)
    }

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
                        navController.navigate("nextScreen") {
                            popUpTo("citySelection") { inclusive = true }
                        }
                    }
                )
            }
            composable("nextScreen") {
                Surface(color = MaterialTheme.colorScheme.background) {
                    Text("Next Screen Content")
                }
            }
        }
    }
}
