package com.bron24.bron24_android.data.network.dto.venue

import com.google.gson.annotations.SerializedName

data class InfrastructureDto(
    @SerializedName("id") val id: Int,
    @SerializedName("raw_infrastructure_name") val name:String,
    @SerializedName("infrastructure_static_name") val staticName:String,
    @SerializedName("infrastructure_description") val description: String,
    @SerializedName("infrastructure_picture") val picture: Boolean,
)