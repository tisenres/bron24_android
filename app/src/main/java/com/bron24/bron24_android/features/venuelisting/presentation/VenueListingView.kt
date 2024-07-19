package com.bron24.bron24_android.features.venuelisting.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bron24.bron24_android.features.venuelisting.domain.model.Venue
import com.bron24.bron24_android.features.venuelisting.presentation.components.VenueCard
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable

@Composable
fun VenueListingView(venues: List<Venue>) {
    LazyColumn(
        contentPadding = PaddingValues(start = 29.dp, end = 21.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(venues) { venue ->
            VenueCard(venue = venue)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewVenueListingView() {

    val sampleVenues = List(5) {
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

    VenueListingView(venues = sampleVenues)
}
