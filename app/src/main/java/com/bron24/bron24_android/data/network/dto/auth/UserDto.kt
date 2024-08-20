package com.bron24.bron24_android.data.network.dto.auth

import com.google.gson.annotations.SerializedName

data class SignupUserDto(
    @SerializedName("phone_number") val phoneNumber: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String
)

data class LoginUserDto(
    @SerializedName("phone_number") val phoneNumber: String,
)

