package com.bron24.bron24_android.screens.home

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bron24.bron24_android.screens.searchfilter.SearchView
import com.bron24.bron24_android.screens.venuelisting.VenueListingView
import androidx.compose.animation.animateContentSize

@Composable
fun HomePage(navController: NavController) {
    var isSearchVisible by remember { mutableStateOf(true) }
    var previousScrollOffset by remember { mutableFloatStateOf(0f) }
    var accumulatedScroll by remember { mutableFloatStateOf(0f) } // New state to accumulate scrolls

    // Анимация с offset для search bar
    val offsetY by animateDpAsState(
        targetValue = if (isSearchVisible) 0.dp else (-150).dp,
        animationSpec = tween(durationMillis = 300),
        label = "SearchViewOffset"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Используем animateContentSize для плавной анимации изменения размеров
            SearchView(
                modifier = Modifier
                    .offset(y = offsetY)
                    .fillMaxWidth()
                    .animateContentSize(animationSpec = tween(300))
            )

            VenueListingView(
                navController = navController,
                onScrollDelta = { delta ->
                    accumulatedScroll += delta - previousScrollOffset

                    // Условие для появления/исчезновения search view
                    if (accumulatedScroll > 50) {
                        isSearchVisible = false
                        accumulatedScroll = 0f
                    } else if (accumulatedScroll < -50) {
                        isSearchVisible = true
                        accumulatedScroll = 0f
                    }

                    previousScrollOffset = delta
                }
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewHomePage() {
    // Preview implementation
}