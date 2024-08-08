package com.bron24.bron24_android.data.network.dto.auth

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("phone_number") val phoneNumber: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("second_name") val secondName: String
)