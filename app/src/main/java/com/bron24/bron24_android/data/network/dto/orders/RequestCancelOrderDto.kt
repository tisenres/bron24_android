package com.bron24.bron24_android.data.network.dto.orders

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class RequestCancelOrderDto(
    @SerializedName("booking_id") val id: Long,
)