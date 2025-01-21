package com.bron24.bron24_android.data.network.dto.auth

import com.bron24.bron24_android.domain.entity.user.UpdateProfile
import com.google.gson.annotations.SerializedName

data class SignupUserDto(
    @SerializedName("phone_number") val phoneNumber: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String
)

data class LoginUserDto(
    @SerializedName("phone_number") val phoneNumber: String,
)

data class UpdateUserDto(
    @SerializedName("success") val success :Boolean,
    @SerializedName("message") val message :String,
    @SerializedName("data") val data:UpdateProfile
)
data class DeleteAccountDto(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data:List<String>
)

