package com.bron24.bron24_android.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bron24.bron24_android.R
import androidx.compose.ui.graphics.Color

@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_bron24), // Replace with your actual logo
            contentDescription = "Logo",
            modifier = Modifier
                .padding(47.dp)
        )
    }

}

@Preview
@Composable
fun PreviewSplashScreen() {
    SplashScreen()
}
