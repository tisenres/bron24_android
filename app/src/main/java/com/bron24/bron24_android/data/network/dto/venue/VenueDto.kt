package com.bron24.bron24_android.data.network.dto.venue

import com.google.gson.annotations.SerializedName

//data class VenueDto(
//    @SerializedName("venue_id") val venueId: Int,
//    @SerializedName("venue_name") val venueName: String,
//    @SerializedName("price_per_hour") val pricePerHour: String,
//    @SerializedName("address") val address: AddressDto
//)

data class VenueResponseDto(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<VenueDto>
)

data class VenueDto(
    @SerializedName("venue_id") val venueId: Int,
    @SerializedName("venue_name") val venueName: String,
    @SerializedName("address") val address: AddressDto,
    @SerializedName("price_per_hour") val pricePerHour: String,
//    @SerializedName("distance") val distance: Double
)