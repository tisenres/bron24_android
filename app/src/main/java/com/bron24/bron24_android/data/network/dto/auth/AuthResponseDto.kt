package com.bron24.bron24_android.data.network.dto.auth

import com.google.gson.annotations.SerializedName

data class AuthResponseDto(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: AuthDataDto?
)

data class AuthDataDto(
    @SerializedName("refresh") val refreshToken: String,
    @SerializedName("access") val accessToken: String,
)