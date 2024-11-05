package com.bron24.bron24_android.data.network.dto.auth

import com.google.gson.annotations.SerializedName

data class AuthResponseDto(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: AuthDataDto?
)

data class AuthDataDto(
    @SerializedName("access") val accessToken: String,
    @SerializedName("refresh") val refreshToken: String,
    @SerializedName("user") val user: UserDto?
)

data class UserDto(
    @SerializedName("user_id") val userId: Int,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String
)