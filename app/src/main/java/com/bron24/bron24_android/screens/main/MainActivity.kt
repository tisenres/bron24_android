package com.bron24.bron24_android.screens.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.compose.rememberNavController
import com.bron24.bron24_android.screens.main.theme.Bron24_androidTheme
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.AndroidEntryPoint
import androidx.hilt.navigation.compose.hiltViewModel
import com.bron24.bron24_android.helper.util.LocaleManager
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var localeManager: LocaleManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.initialize(this)
        setContent {
            val viewModelStoreOwner = this as ViewModelStoreOwner
            val navController = rememberNavController()

            navController.setViewModelStore(viewModelStoreOwner.viewModelStore)

            CompositionLocalProvider(LocalViewModelStoreOwner provides viewModelStoreOwner) {
                Bron24_androidTheme {
                    val mainViewModel: MainViewModel = hiltViewModel()
                    OnboardingNavHost(navController = navController, mainViewModel = mainViewModel)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        localeManager.applySavedLocale(this)
    }

    override fun onStop() {
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }
}