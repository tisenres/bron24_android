package com.bron24.bron24_android.data.network.dto.orders

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ResponseOrderDetailsDto(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: OrderDetailsDto
)