package com.bron24.bron24_android.features.venuelisting.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bron24.bron24_android.features.venuelisting.presentation.components.VenueCard
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.tooling.preview.Preview
import com.bron24.bron24_android.features.venuelisting.domain.entities.Venue

@Composable
fun VenueListingView(
    viewModel: VenueListingViewModel = hiltViewModel()
) {
    val venues by viewModel.venues.collectAsState()

    LazyColumn(
        contentPadding = PaddingValues(horizontal = 25.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(venues) { venue ->
            VenueCard(venue = venue, false)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewVenueListingView() {
    // Here you can use a sample venue list for preview
    val sampleVenues = listOf(
        Venue(
            venueId = 1,
            name = "Sample Venue",
            type = "Indoor",
            surface = "Grass",
            capacity = 100,
            sportType = "Football",
            price = "100",
            description = "Sample description",
            workingHoursFrom = "09:00",
            workingHoursTill = "21:00",
            contact1 = "1234567890",
            contact2 = null,
            city = 1,
            infrastructure = 1,
            address = 3,
            owner = 1,
            distance = "5 km",
            rating = "4.5",
            freeSlots = "10",
            imageUrl = "",
            overlayImageUrl = ""
        )
    )

    VenueListingView(viewModel = hiltViewModel())
}
