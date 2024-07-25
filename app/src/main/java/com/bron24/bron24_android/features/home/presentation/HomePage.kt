package com.bron24.bron24_android.features.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bron24.bron24_android.features.filter.presentetion.SearchView
import com.bron24.bron24_android.features.venuelisting.presentation.VenueListingView

@Composable
fun HomePage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        SearchView(modifier = Modifier.padding(bottom = 17.dp))
        VenueListingView()
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewHomePage() {
    HomePage()
}