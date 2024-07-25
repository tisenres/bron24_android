package com.bron24.bron24_android.core.domain.entities

data class Venue(
    val venueId: Int,
    val venueName: String,
    val venueType: String,
    val venueSurface: String,
    val peopleCapacity: Int,
    val sportType: String,
    val pricePerHour: String,
    val description: String,
    val workingHoursFrom: String,
    val workingHoursTill: String,
    val contact1: String,
    val contact2: String,
    val createdAt: String,
    val updatedAt: String,
    val previewImage: String,
    val address: Address,
    val city: City,
    val infrastructure: Infrastructure,
    val venueOwner: VenueOwner
)
