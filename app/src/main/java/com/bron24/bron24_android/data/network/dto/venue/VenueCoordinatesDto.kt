package com.bron24.bron24_android.data.network.dto.venue

import com.google.gson.annotations.SerializedName

data class VenueCoordinatesDto(
    @SerializedName("venue_id") val venueId: Int,
    @SerializedName("venue_name") val venueName: String,
    @SerializedName("latitude") val latitude: String,
    @SerializedName("longitude") val longitude: String
)