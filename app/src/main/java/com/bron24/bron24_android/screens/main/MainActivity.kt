package com.bron24.bron24_android.screens.main

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bron24.bron24_android.screens.main.theme.Bron24_androidTheme
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.AndroidEntryPoint
import com.bron24.bron24_android.helper.util.LocaleManager
import com.bron24.bron24_android.helper.util.NetworkConnection
import com.bron24.bron24_android.helper.util.presentation.AuthEvent
import com.bron24.bron24_android.helper.util.presentation.GlobalAuthEventBus
import com.bron24.bron24_android.helper.util.presentation.components.toast.ObserveToast
import com.bron24.bron24_android.helper.util.presentation.components.toast.ToastManager
import com.bron24.bron24_android.helper.util.presentation.components.toast.ToastType
import com.bron24.bron24_android.screens.auth.AuthState
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var localeManager: LocaleManager
    private lateinit var navController: NavHostController
//    private lateinit var networkConnection: NetworkConnection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.initialize(this)
        setContent {
            val viewModelStoreOwner = this as ViewModelStoreOwner
            navController = rememberNavController()

            navController.setViewModelStore(viewModelStoreOwner.viewModelStore)

//            networkConnection = NetworkConnection(applicationContext)

            CompositionLocalProvider(LocalViewModelStoreOwner provides viewModelStoreOwner) {
                Bron24_androidTheme {
                    val mainViewModel: MainViewModel = hiltViewModel()
                    OnboardingNavHost(navController = navController, mainViewModel = mainViewModel)
                    ObserveToast()

//                    NetworkErrorToastHandler(networkConnection)
                }
            }

//            networkConnection.observe(this) { isConnected ->
//                if (!isConnected) {
//                    Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
//                }
//            }
        }
        // Observe Global Auth Events
        observeAuthEvents()
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

    @Composable
    fun NetworkErrorToastHandler(networkConnection: NetworkConnection) {
        val isConnected by networkConnection.observeAsState(initial = true)

        LaunchedEffect(isConnected) {
            if (!isConnected) {
                ToastManager.showToast(
                    "No internet connection",
                    ToastType.ERROR
                )
            }
        }
    }

    private fun observeAuthEvents() {
        lifecycleScope.launch {
            GlobalAuthEventBus.authEvents.collect { event ->
                when (event) {
                    is AuthEvent.TokenRefreshFailed -> handleTokenRefreshFailure()
                }
            }
        }
    }

    private fun handleTokenRefreshFailure() {
        Log.d("MainActivity", "Token refresh failed, navigating to login")
        runOnUiThread {
            navController.navigate(Screen.PhoneNumberInput.route) {
                popUpTo(navController.graph.startDestinationId) { inclusive = true }
            }
        }
    }
}
