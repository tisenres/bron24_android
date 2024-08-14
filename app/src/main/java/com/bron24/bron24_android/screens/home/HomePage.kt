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

@Composable
fun HomePage(navController: NavController) {
    var isSearchVisible by remember { mutableStateOf(true) }
    var previousScrollOffset by remember { mutableFloatStateOf(0f) }
    var accumulatedScroll by remember { mutableFloatStateOf(0f) }

    // Определяем высоту search bar с анимацией
    val searchViewHeight by animateDpAsState(
        targetValue = if (isSearchVisible) 150.dp else 0.dp, // Замените 90.dp на фактическую высоту SearchView
        animationSpec = tween(durationMillis = 300),
        label = "SearchViewHeight"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Изменяем высоту SearchView, чтобы при скрытии другие элементы занимали её место
            Box(
                modifier = Modifier
                    .height(searchViewHeight)
                    .fillMaxWidth()
            ) {
                if (isSearchVisible) {
                    SearchView(modifier = Modifier.fillMaxWidth())
                }
            }

            VenueListingView(
                navController = navController,
                onScrollDelta = { delta ->
                    accumulatedScroll += delta - previousScrollOffset

                    // Условие для появления/исчезновения search view
                    if (accumulatedScroll > 100) {
                        isSearchVisible = false
                        accumulatedScroll = 0f
                    } else if (accumulatedScroll < -100) {
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