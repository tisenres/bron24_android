package com.bron24.bron24_android.data.network.dto.booking

import com.google.gson.annotations.SerializedName

data class AvailableTimesRequestDto(
    @SerializedName("venue_id") val venueId: Int,
    @SerializedName("date") val date: String,
    @SerializedName("sector") val sector: String
)

data class AvailableTimesResponseDto(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: AvailableTimesDataDto,
)

data class AvailableTimesDataDto(
    @SerializedName("time_stamps") val timeStamps: List<TimeStampDto>,
)

data class TimeStampDto(
    @SerializedName("start_time") val startTime: String,
    @SerializedName("end_time") val endTime: String
)
