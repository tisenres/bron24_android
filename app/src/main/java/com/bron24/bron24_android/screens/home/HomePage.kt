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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.ui.platform.LocalDensity

@Composable
fun HomePage(navController: NavController) {
    val listState = rememberLazyListState()
    var isSearchVisible by remember { mutableStateOf(true) }
    var totalOffset by remember { mutableFloatStateOf(0f) }
    var previousScrollOffset by remember { mutableFloatStateOf(0f) }

    val searchViewHeight = 90.dp // Высота SearchView
    val maxOffset = with(LocalDensity.current) { searchViewHeight.toPx() }

    // Увеличиваем порог для активации анимации
    val scrollThreshold = 150f

    // Анимация смещения SearchView
    val animatedOffset by animateFloatAsState(
        targetValue = totalOffset.coerceIn(-maxOffset, 0f),
        animationSpec = tween(durationMillis = 300), label = ""
    )

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemScrollOffset + listState.firstVisibleItemIndex * listState.layoutInfo.viewportEndOffset }
            .collect { offset ->
                val delta = offset - previousScrollOffset

                if (delta > scrollThreshold) { // Скролл вниз, SearchView смещается вверх
                    totalOffset = (totalOffset - delta).coerceIn(-maxOffset, 0f)
                } else if (delta < -scrollThreshold && listState.firstVisibleItemIndex == 0) { // Скролл вверх, SearchView возвращается
                    totalOffset = (totalOffset - delta).coerceIn(-maxOffset, 0f)
                }

                // Проверяем видимость SearchView
                isSearchVisible = totalOffset > -maxOffset

                previousScrollOffset = offset.toFloat()
            }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Убираем белый фон и оставляем только SearchView
            SearchView(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = animatedOffset.dp)
            )

            VenueListingView(
                navController = navController,
                listState = listState
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewHomePage() {
    // Preview implementation
}