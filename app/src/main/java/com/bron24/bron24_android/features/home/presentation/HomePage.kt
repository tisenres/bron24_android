package com.bron24.bron24_android.features.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.bron24.bron24_android.features.filter.presentetion.SearchView
import com.bron24.bron24_android.features.venuelisting.domain.model.Venue
import com.bron24.bron24_android.features.venuelisting.presentation.VenueListingView

@Composable
fun HomePage() {
    val sampleVenues = List(100) {
        Venue(
            name = "Bunyodkor kompleksi ${it + 1}",
            address = "Mustaqillik maydoni, Tashkent, Uzbekistan",
            distance = "${(1..10).random()}km",
            rating = "${(1..5).random()}.0",
            price = "${(50..150).random()}sum/hour",
            freeSlots = "${(5..20).random()} slots today",
            imageUrl = "https://via.placeholder.com/340x82",
            overlayImageUrl = "https://via.placeholder.com/21x25"
        )
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
    ) {
        SearchView(modifier = Modifier.padding(top = 24.dp, start = 25.dp, end = 25.dp, bottom = 19.dp))
        VenueListingView(venues = sampleVenues)
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewHomePage() {
    HomePage()
}
