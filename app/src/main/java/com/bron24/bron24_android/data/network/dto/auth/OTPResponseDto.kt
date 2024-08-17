package com.bron24.bron24_android.data.network.dto.auth

import com.google.gson.annotations.SerializedName

data class PhoneNumberResponseDto(
    @SerializedName("result") val result: String
)

data class OTPCodeResponseDto(
    @SerializedName("status") val status: String,
    @SerializedName("user_exists") val userExists: Boolean
)