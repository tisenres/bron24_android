package com.bron24.bron24_android.data.network.dto

import com.google.gson.annotations.SerializedName

data class VenueCoordinatesDto(
    @SerializedName("venue_name") val venueName: String,
    @SerializedName("latitude") val latitude: String,
    @SerializedName("longitude") val longitude: String
)