package com.bron24.bron24_android.screens.home

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
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
    val listState = rememberLazyListState()
    var previousIndex by remember { mutableStateOf(0) }
    var previousScrollOffset by remember { mutableStateOf(0) }
    var isSearchVisible by remember { mutableStateOf(true) }

    val offsetY = animateDpAsState(
        targetValue = if (isSearchVisible) 0.dp else (-100).dp, // Adjust offset to hide or show SearchView
        animationSpec = androidx.compose.animation.core.tween(durationMillis = 300), label = ""
    )

    LaunchedEffect(remember { derivedStateOf { listState.firstVisibleItemIndex } }, listState.firstVisibleItemScrollOffset) {
        if (listState.firstVisibleItemIndex == previousIndex) {
            if (listState.firstVisibleItemScrollOffset > previousScrollOffset) {
                isSearchVisible = false
            } else if (listState.firstVisibleItemScrollOffset < previousScrollOffset) {
                isSearchVisible = true
            }
        } else if (listState.firstVisibleItemIndex > previousIndex) {
            isSearchVisible = false
        } else if (listState.firstVisibleItemIndex < previousIndex) {
            isSearchVisible = true
        }

        previousIndex = listState.firstVisibleItemIndex
        previousScrollOffset = listState.firstVisibleItemScrollOffset
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
            SearchView(
                modifier = Modifier
                    .offset(y = offsetY.value)
                    .fillMaxWidth()
            )
//            Spacer(modifier = Modifier.height(10.dp))
            VenueListingView(navController = navController)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewHomePage() {
}