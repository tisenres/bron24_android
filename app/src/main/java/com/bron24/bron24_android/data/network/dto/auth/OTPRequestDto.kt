package com.bron24.bron24_android.data.network.dto.auth

import com.google.gson.annotations.SerializedName

data class OTPRequestDto(
    @SerializedName("phone_number") val phoneNumber: String,
    @SerializedName("otp") val otp: Int? = null
)