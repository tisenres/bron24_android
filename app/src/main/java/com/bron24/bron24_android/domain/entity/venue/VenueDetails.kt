package com.bron24.bron24_android.domain.entity.venue

data class VenueDetails(
    val venueId: Int,
    val address: Address,
    val city: City,
    val infrastructure: List<Infrastructure>,
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
    val latitude: Double,
    val longitude: Double,
    val distance: Double,
    val sectors: List<String>
)
