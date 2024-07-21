package com.bron24.bron24_android.features.venuelisting.data.dto

import com.google.gson.annotations.SerializedName

data class VenueDto(
    @SerializedName("venue_id") val venueId: Int,
    @SerializedName("venue_name") val venueName: String,
    @SerializedName("venue_type") val venueType: String,
    @SerializedName("venue_surface") val venueSurface: String,
    @SerializedName("people_capacity") val peopleCapacity: Int,
    @SerializedName("sport_type") val sportType: String,
    @SerializedName("price_per_hour") val pricePerHour: String,
    @SerializedName("description") val description: String,
    @SerializedName("working_hours_from") val workingHoursFrom: String,
    @SerializedName("working_hours_till") val workingHoursTill: String,
    @SerializedName("contact_1") val contact1: String,
    @SerializedName("contact_2") val contact2: String?,
    @SerializedName("city") val city: Int,
    @SerializedName("infrastructure") val infrastructure: Int,
    @SerializedName("address") val address: Int,
    @SerializedName("venue_owner") val venueOwner: Int
)
