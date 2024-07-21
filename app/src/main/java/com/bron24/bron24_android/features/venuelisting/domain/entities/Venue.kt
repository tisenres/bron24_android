package com.bron24.bron24_android.features.venuelisting.domain.entities

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
    val distance: String = "5 km",
    val rating: String = "4.5",
    val freeSlots: String = "10",
    val imageUrl: String = "",
    val overlayImageUrl: String = ""
)
