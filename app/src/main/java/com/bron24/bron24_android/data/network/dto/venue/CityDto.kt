package com.bron24.bron24_android.data.network.dto.venue

import com.google.gson.annotations.SerializedName

data class CityDto(
    @SerializedName("id") val id: Int,
    @SerializedName("city_name") val cityName: String
)