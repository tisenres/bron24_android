package com.bron24.bron24_android.domain.entity.venue

data class VenueCoordinates(
    val venueId: Int,
    val venueName: String,
    val latitude: String,
    val longitude: String,
    val selected:Boolean = false
)