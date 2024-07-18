package com.bron24.bron24_android.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bron24.bron24_android.core.presentation.theme.Bron24_androidTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Use the ViewModelStoreOwner from the activity context
            val viewModelStoreOwner = this as ViewModelStoreOwner
            val navController = rememberNavController()

            // Set the ViewModelStore on the NavController
            navController.setViewModelStore(viewModelStoreOwner.viewModelStore)

            CompositionLocalProvider(LocalViewModelStoreOwner provides viewModelStoreOwner) {
                Bron24_androidTheme {
                    NavScreen(navController = navController)
                }
            }
        }
    }
}
