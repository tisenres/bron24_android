package com.bron24.bron24_android.screens.main

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat

@Composable
fun SetStatusBarColor(color: Color, darkIcons: Boolean = true) {
    val activity = (LocalContext.current as? Activity)
    val window = activity?.window ?: return
    val view = LocalView.current

    SideEffect {
        window.statusBarColor = color.toArgb() // Set the status bar color
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val windowInsetsController = WindowInsetsControllerCompat(window, view)
        windowInsetsController.isAppearanceLightStatusBars = darkIcons
    }

//    DisposableEffect(Unit) {
//        window.statusBarColor = color.toArgb()
//        val windowInsetsController = WindowInsetsControllerCompat(window, view)
//        windowInsetsController.isAppearanceLightStatusBars = darkIcons
//
//        onDispose {
//            window.statusBarColor = Color.White.toArgb()
//            val windowInsetsController = WindowInsetsControllerCompat(window, view)
//            windowInsetsController.isAppearanceLightStatusBars = darkIcons
//        }
//    }

}
