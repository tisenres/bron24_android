package com.bron24.bron24_android.screens.main

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.FadeTransition
import cafe.adriel.voyager.transitions.ScaleTransition
import cafe.adriel.voyager.transitions.SlideTransition
import com.bron24.bron24_android.components.items.ErrorNetWork
import com.bron24.bron24_android.components.toast.ObserveToast
import com.bron24.bron24_android.helper.util.LocaleManager
import com.bron24.bron24_android.helper.util.NetworkConnection
import com.bron24.bron24_android.helper.util.presentation.AuthEvent
import com.bron24.bron24_android.helper.util.presentation.GlobalAuthEventBus
import com.bron24.bron24_android.components.toast.ToastManager
import com.bron24.bron24_android.components.toast.ToastType
import com.bron24.bron24_android.data.local.preference.LocalStorage
import com.bron24.bron24_android.helper.util.presentation.logOut
import com.bron24.bron24_android.navigator.NavigationHandler
import com.bron24.bron24_android.screens.main.theme.Bron24_androidTheme
import com.bron24.bron24_android.screens.splash.SplashScreen
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var localeManager: LocaleManager

    @Inject
    lateinit var localStorage: LocalStorage

    @Inject
    lateinit var navigationHandler: NavigationHandler

    private lateinit var navController: NavHostController

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.initialize(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            checkNotificationPermission()
        }
        setContent {
            val viewModelStoreOwner = this as ViewModelStoreOwner
            navController = rememberNavController()

            navController.setViewModelStore(viewModelStoreOwner.viewModelStore)

            val networkConnection = NetworkConnection(applicationContext)
            Bron24_androidTheme {
                Navigator(screen = SplashScreen()) { navigator ->
                    navigationHandler.screenState.onEach {
                        it.invoke(navigator)
                    }.launchIn(lifecycleScope)
                    CurrentScreen()
                    networkConnection.observe(this) { isConnected ->

                    }
                    ObserveToast()
                    NetworkErrorToastHandler(networkConnection)

                }
                networkConnection.observe(this) { isConnected ->
                    if (!isConnected) {
                        Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
                    }
                }

            }
            val context = LocalContext.current
            val activity = context as Activity
            logOut {
                localStorage.openMenu = false
                activity.finish()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        localeManager.applySavedLocale()
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

    private val requestNotificationPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) Timber.tag("Permission").d("Notification Permission granted")
            else Timber.tag("Permission").w("Notification Permission denied")
        }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun checkNotificationPermission() {
        val permission = Manifest.permission.POST_NOTIFICATIONS
        when {
            ContextCompat.checkSelfPermission(
                this,
                permission
            ) == PackageManager.PERMISSION_GRANTED -> {
                // make your action here
            }

            shouldShowRequestPermissionRationale(permission) -> {
//                showPermissionRationaleDialog()
            }

            else -> {
                requestNotificationPermission.launch(permission)
            }
        }
    }
}
