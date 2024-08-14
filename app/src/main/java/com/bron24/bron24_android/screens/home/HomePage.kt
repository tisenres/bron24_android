package com.bron24.bron24_android.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bron24.bron24_android.screens.searchfilter.SearchView
import com.bron24.bron24_android.screens.venuelisting.VenueListingView

@Composable
fun HomePage(navController: NavController) {
    val listState = rememberLazyListState()
    var isSearchVisible by remember { mutableStateOf(true) }
    var totalOffset by remember { mutableFloatStateOf(0f) }
    var previousScrollOffset by remember { mutableFloatStateOf(0f) }

    val searchViewMaxHeight = 150.dp // Максимальная высота SearchView
    val maxOffset = with(LocalDensity.current) { searchViewMaxHeight.toPx() }

    // Увеличиваем порог для активации анимации
    val scrollThreshold = 500f

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemScrollOffset + listState.firstVisibleItemIndex * listState.layoutInfo.viewportEndOffset }
            .collect { offset ->
                val delta = offset - previousScrollOffset

                if (delta > scrollThreshold && totalOffset > -maxOffset) { // Скролл вниз, уменьшаем высоту SearchView
                    totalOffset = (totalOffset - delta).coerceIn(-maxOffset, 0f)
                    isSearchVisible = false
                } else if (delta < -scrollThreshold && listState.firstVisibleItemIndex == 0 && totalOffset < 0f) { // Скролл вверх, восстанавливаем высоту SearchView
                    totalOffset = (totalOffset - delta).coerceIn(-maxOffset, 0f)
                    isSearchVisible = true
                }

                previousScrollOffset = offset.toFloat()
            }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Плавное исчезновение/появление SearchView без белого фона
            AnimatedVisibility(
                visible = isSearchVisible,
                enter = fadeIn(animationSpec = tween(300)) + expandVertically(animationSpec = tween(300)),
                exit = fadeOut(animationSpec = tween(300)) + shrinkVertically(animationSpec = tween(300))
            ) {
                SearchView(modifier = Modifier.fillMaxWidth())
            }

            VenueListingView(
                navController = navController,
                listState = listState
            )
        }
    }
}