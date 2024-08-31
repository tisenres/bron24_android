package com.bron24.bron24_android.domain.entity.venue

data class VenueDetails(
    val venueId: Int,
    val address: Address,
    val city: City,
    val infrastructure: Infrastructure,
    val venueOwner: VenueOwner?,
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
    var imageUrls: List<String> = emptyList(),
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)
