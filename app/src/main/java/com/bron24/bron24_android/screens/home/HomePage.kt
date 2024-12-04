package com.bron24.bron24_android.screens.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bron24.bron24_android.screens.main.theme.Success
import com.bron24.bron24_android.screens.searchfilter.SearchView
import com.bron24.bron24_android.screens.venuelisting.VenueListingView
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun HomePage(navController: NavController) {
    val listState = rememberLazyListState()
    Log.d("AAA", "HomePage: openMenu")
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
                viewModel = hiltViewModel(),
                modifier = Modifier.fillMaxWidth(),
                navController = navController,
            )

            VenueListingView(
                navController = navController,
                listState = listState,
            )
        }
    }
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(Success)
}