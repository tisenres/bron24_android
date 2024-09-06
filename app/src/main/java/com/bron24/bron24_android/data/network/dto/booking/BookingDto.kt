package com.bron24.bron24_android.data.network.dto.booking

import com.google.gson.annotations.SerializedName

data class BookingDto(
    @SerializedName("id") val id: String,
    @SerializedName("venue_id") val venueId: Int,
    @SerializedName("user_id") val userId: String,
    @SerializedName("start_time") val startTime: Long,
    @SerializedName("end_time") val endTime: Long,
    @SerializedName("status") val status: String,
//    @SerializedName("created_at") val createdAt: String,
//    @SerializedName("updated_at") val updatedAt: String
)