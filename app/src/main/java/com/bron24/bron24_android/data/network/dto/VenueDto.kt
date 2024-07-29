package com.bron24.bron24_android.data.network.dto

import com.google.gson.annotations.SerializedName

data class VenueDto(
    @SerializedName("venue_name") val venueName: String,
    @SerializedName("price_per_hour") val pricePerHour: String,
    @SerializedName("address") val address: AddressDto
)

data class AddressDto(
    @SerializedName("id") val id: Int,
    @SerializedName("address_name") val addressName: String,
    @SerializedName("district") val district: String,
    @SerializedName("closest_metro_station") val closestMetroStation: String
)

data class VenueCoordinatesDto(
    @SerializedName("venue_name") val venueName: String,
    @SerializedName("latitude") val latitude: String,
    @SerializedName("longitude") val longitude: String
)