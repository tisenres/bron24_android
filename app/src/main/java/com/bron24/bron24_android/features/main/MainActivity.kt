package com.bron24.bron24_android.features.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.compose.rememberNavController
import com.bron24.bron24_android.features.main.theme.Bron24_androidTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModelStoreOwner = this as ViewModelStoreOwner
            val navController = rememberNavController()

            navController.setViewModelStore(viewModelStoreOwner.viewModelStore)

            CompositionLocalProvider(LocalViewModelStoreOwner provides viewModelStoreOwner) {
                Bron24_androidTheme {
                    NavScreen(navController = navController)
                }
            }
        }
    }
}
