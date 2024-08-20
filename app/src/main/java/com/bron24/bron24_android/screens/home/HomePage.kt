package com.bron24.bron24_android.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.bron24.bron24_android.screens.searchfilter.SearchView
import com.bron24.bron24_android.screens.venuelisting.VenueListingView

@Composable
fun HomePage(navController: NavController) {
    val listState = rememberLazyListState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Simply displaying the SearchView without any animations
            SearchView(modifier = Modifier.fillMaxWidth())

            // Displaying the VenueListingView
            VenueListingView(
                navController = navController,
                listState = listState
            )
        }
    }
}