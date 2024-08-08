package com.bron24.bron24_android.data.network.dto.auth

import com.google.gson.annotations.SerializedName

data class PhoneNumberResponseDto(
    @SerializedName("success") val success: List<String>
)

data class OTPCodeResponseDto(
    @SerializedName("status") val status: String
)