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
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(venues) { venue ->
            VenueCard(venue = venue)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewVenueListingView() {
    val sampleVenues = listOf(
        Venue(
            name = "Bunyodkor kompleksi",
            address = "Mustaqillik maydoni, Tashkent, Uzbekistan",
            distance = "3km",
            rating = "4.0",
            price = "100sum/hour",
            freeSlots = "14 slots today",
            imageUrl = "https://via.placeholder.com/340x82",
            overlayImageUrl = "https://via.placeholder.com/21x25"
        ),
        // Add more sample venues if needed
    )

    VenueListingView(venues = sampleVenues)
}
