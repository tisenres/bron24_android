package com.bron24.bron24_android.screens.venuedetails

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument

@Composable
fun VenueDetailsScreen(viewModel: VenueDetailsViewModel, venueId: Int) {
    Text(venueId.toString())
}