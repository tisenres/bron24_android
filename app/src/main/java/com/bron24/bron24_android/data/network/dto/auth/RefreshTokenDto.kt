package com.bron24.bron24_android.data.network.dto.auth

import com.google.gson.annotations.SerializedName

data class RefreshTokenDto(
    @SerializedName("refresh") val refreshToken: String
)