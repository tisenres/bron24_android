package com.bron24.bron24_android.features.venuelisting

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bron24.bron24_android.features.components.VenueCard
import com.bron24.bron24_android.features.main.theme.interFontFamily
import com.bron24.bron24_android.features.adssection.AdsSection

@Composable
fun VenueListingView(
    modifier: Modifier = Modifier,
    viewModel: VenueListingViewModel = hiltViewModel()
) {
    val venues by viewModel.venues.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LazyColumn(
        contentPadding = PaddingValues(horizontal = 25.dp),
        modifier = modifier
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
            AdsSection()
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Football fields",
                style = TextStyle(
                    fontFamily = interFontFamily,
                    fontWeight = FontWeight(600),
                    fontSize = 24.sp,
                    lineHeight = 30.sp,
                    color = Color.Black
                ),
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        if (isLoading) {
            items(5) {
                VenueCard(isLoading = true)
            }
        } else {
            items(venues) { venue ->
                VenueCard(venue = venue, isLoading = false)
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewVenueListingView() {
    // Placeholder for Venue model and sample data
    data class Venue(
        val venueId: Int,
        val name: String,
        val type: String,
        val surface: String,
        val capacity: Int,
        val sportType: String,
        val price: String,
        val description: String,
        val workingHoursFrom: String,
        val workingHoursTill: String,
        val contact1: String,
        val contact2: String?,
        val city: Int,
        val infrastructure: Int,
        val address: Int,
        val owner: Int,
        val distance: String,
        val rating: String,
        val freeSlots: String,
        val imageUrl: String,
        val overlayImageUrl: String
    )

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

    VenueListingView(
        modifier = Modifier.fillMaxWidth(),
        viewModel = hiltViewModel()
    )
}