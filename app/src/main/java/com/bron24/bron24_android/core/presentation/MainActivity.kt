package com.bron24.bron24_android.core.presentation

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bron24.bron24_android.core.presentation.theme.Bron24_androidTheme
import com.bron24.bron24_android.features.language.domain.LocaleManager
import com.bron24.bron24_android.features.language.presentation.LanguageSelectionScreen
import com.bron24.bron24_android.features.location.presentation.LocationRequestScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Bron24_androidTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AppNavigator()
                }
            }
        }
    }

    override fun attachBaseContext(newBase: Context) {
        val sharedPreferences = newBase.getSharedPreferences("settings", Context.MODE_PRIVATE)
        val languageCode = sharedPreferences.getString("selected_language", "uz") ?: "uz"
        val context = LocaleManager.setLocale(newBase, languageCode)
        super.attachBaseContext(context)
    }
}

@Composable
fun AppNavigator() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "languageSelection") {
        composable("languageSelection") {
            LanguageSelectionScreen(
                viewModel = hiltViewModel(),
                onNavigateToLocationRequest = {
                    navController.navigate("locationRequest") {
                        popUpTo("languageSelection") { inclusive = true }
                    }
                }
            )
        }
        composable("locationRequest") {
            LocationRequestScreen(
                onAllowClick = { /* Handle allow click */ },
                onDenyClick = { /* Handle deny click */ }
            )
        }
    }
}
