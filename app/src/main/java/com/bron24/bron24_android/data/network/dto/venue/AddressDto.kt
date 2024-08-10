package com.bron24.bron24_android.data.network.dto.venue

import com.google.gson.annotations.SerializedName

data class AddressDto(
    @SerializedName("id") val id: Int,
    @SerializedName("address_name") val addressName: String,
    @SerializedName("longitude") val longitude: String,
    @SerializedName("latitude") val latitude: String,
    @SerializedName("district") val district: String,
    @SerializedName("closest_metro_station") val closestMetroStation: String
)