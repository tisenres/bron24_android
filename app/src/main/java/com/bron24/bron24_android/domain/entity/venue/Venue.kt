package com.bron24.bron24_android.domain.entity.venue

data class Venue(
    val venueId: Int,
    val venueName: String,
    val pricePerHour: String,
    val address: Address,
    val imageUrls: List<String>?
)