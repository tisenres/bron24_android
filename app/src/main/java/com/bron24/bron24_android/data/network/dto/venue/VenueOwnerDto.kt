package com.bron24.bron24_android.data.network.dto.venue

import com.google.gson.annotations.SerializedName

data class VenueOwnerDto(
    @SerializedName("id") val id: Int,
    @SerializedName("owner_name") val ownerName: String,
    @SerializedName("tin_number") val tinNumber: Int,
    @SerializedName("contact_1") val contact1: String,
    @SerializedName("contact_2") val contact2: String
)