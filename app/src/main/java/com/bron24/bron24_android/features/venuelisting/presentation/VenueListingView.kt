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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.bron24.bron24_android.core.presentation.theme.interFontFamily

@Composable
fun VenueListingView(
    modifier: Modifier = Modifier,
    viewModel: VenueListingViewModel = hiltViewModel()
) {
    val venues by viewModel.venues.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LazyColumn(
        contentPadding = PaddingValues(horizontal = 25.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Special Offers",
                style = TextStyle(
                    fontFamily = interFontFamily,
                    fontWeight = FontWeight(600),
                    fontSize = 24.sp,
                    lineHeight = 30.sp,
                    color = Color.Black
                )
            )
            Text(
                text = "See all",
                style = TextStyle(
                    fontFamily = interFontFamily,
                    fontWeight = FontWeight(600),
                    fontSize = 16.sp,
                    lineHeight = 20.sp,
                    color = Color(0xFF32B768)
                )
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (isLoading) {
            items(5) {
                VenueCard(isLoading = true)
            }
        } else {
            items(venues) { venue ->
                VenueCard(venue = venue, isLoading = false)
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewVenueListingView() {
    // Here you can use a sample venue list for preview
//    val sampleVenues = listOf(
//        Venue(
//            venueId = 1,
//            name = "Sample Venue",
//            type = "Indoor",
//            surface = "Grass",
//            capacity = 100,
//            sportType = "Football",
//            price = "100",
//            description = "Sample description",
//            workingHoursFrom = "09:00",
//            workingHoursTill = "21:00",
//            contact1 = "1234567890",
//            contact2 = null,
//            city = 1,
//            infrastructure = 1,
//            address = 3,
//            owner = 1,
//            distance = "5 km",
//            rating = "4.5",
//            freeSlots = "10",
//            imageUrl = "",
//            overlayImageUrl = ""
//        )
//    )

    VenueListingView(viewModel = hiltViewModel())
}