package com.bron24.bron24_android.data.network.dto.auth

import com.google.gson.annotations.SerializedName

data class AuthResponseDto(
    @SerializedName("refresh") val refreshToken: String,
    @SerializedName("access") val accessToken: String,
//    @SerializedName("message") val message: String,
)