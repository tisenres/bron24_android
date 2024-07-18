package com.bron24.bron24_android.features.home.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.bron24.bron24_android.features.filter.presentetion.SearchView
import com.bron24.bron24_android.features.venuelisting.domain.model.Venue
import com.bron24.bron24_android.features.venuelisting.presentation.components.VenueCard

@Composable
fun HomePage() {
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
    )

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            SearchView()
        }
        items(sampleVenues) { venue ->
            VenueCard(venue = venue)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewHomePage() {
    HomePage()
}
