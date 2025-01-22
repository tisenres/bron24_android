package com.bron24.bron24_android.data.network.dto.venue

import com.google.gson.annotations.SerializedName

data class SpecialOffersDto(
    @SerializedName("success") val success: Boolean = false,
    @SerializedName("message") val message: String? = null,
    @SerializedName("data") val data: List<SpecialOfferDto>? = emptyList()
)

data class SpecialOfferDto(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("image") val image: String? = null,
    @SerializedName("created_at") val createdAt: String? = null,
    @SerializedName("valid_until") val validUntil: String? = null
)