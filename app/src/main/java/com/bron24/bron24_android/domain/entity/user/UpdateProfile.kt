package com.bron24.bron24_android.domain.entity.user

import com.google.gson.annotations.SerializedName

data class UpdateProfile(
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String
)

data class DeleteAcc(
    @SerializedName("delete_type") val deleteType:String
)