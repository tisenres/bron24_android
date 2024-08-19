package com.bron24.bron24_android.data.network.dto.auth

import com.google.gson.annotations.SerializedName

data class PhoneNumberResponseDto(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
//    @SerializedName("data") val data: Data
)

data class OTPCodeResponseDto(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: OTPDataDto
)

data class OTPDataDto(
    @SerializedName("user_exists") val userExists: Boolean,
)