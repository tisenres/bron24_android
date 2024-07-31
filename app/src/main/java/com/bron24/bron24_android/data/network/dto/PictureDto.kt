package com.bron24.bron24_android.data.network.dto

import com.google.gson.annotations.SerializedName

data class PictureDto(
    @SerializedName("id") val id: Int,
    @SerializedName("url") val url: String,
    @SerializedName("venue_id") val venueId: Int,
)