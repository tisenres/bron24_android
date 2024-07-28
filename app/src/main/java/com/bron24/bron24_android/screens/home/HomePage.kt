package com.bron24.bron24_android.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.bron24.bron24_android.screens.filter.SearchView
import com.bron24.bron24_android.screens.venuelisting.VenueListingView

@Composable
fun HomePage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        SearchView()
        VenueListingView()
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewHomePage() {
    HomePage()
}