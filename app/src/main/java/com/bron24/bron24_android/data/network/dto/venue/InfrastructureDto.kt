package com.bron24.bron24_android.data.network.dto.venue

import com.google.gson.annotations.SerializedName

data class InfrastructureDto(
    @SerializedName("id") val id: Int,
    @SerializedName("infrastructure_name") val infrastructureName: String,
    @SerializedName("infrastructure_description") val infrastructureDescription: String?,
    @SerializedName("infrastructure_picture") val infrastructurePicture: String?,

//    @SerializedName("locker_room") val lockerRoom: Boolean,
//    @SerializedName("stands") val stands: String,
//    @SerializedName("shower") val shower: Boolean,
//    @SerializedName("parking") val parking: Boolean
)