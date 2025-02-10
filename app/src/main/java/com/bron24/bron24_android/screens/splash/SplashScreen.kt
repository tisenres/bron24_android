package com.bron24.bron24_android.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.bron24.bron24_android.R

class SplashScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: SplashScreenContract.ViewModel = getViewModel<SplashScreenVM>()
        SplashScreenContent()
    }

}

@Composable
fun SplashScreenContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.bron24_logo2),
            contentDescription = "Logo",
            modifier = Modifier
                .fillMaxSize()
                .padding(60.dp)
        )
    }
}

@Preview
@Composable
fun PreviewSplashScreen() {
    SplashScreenContent()
}
