package com.bron24.bron24_android.data.network.mappers

import com.bron24.bron24_android.data.network.dto.booking.AvailableTimesResponseDto
import com.bron24.bron24_android.data.network.dto.booking.TimeStampDto
import com.bron24.bron24_android.domain.entity.booking.TimeSlot
import com.bron24.bron24_android.domain.entity.booking.AvailableTimesResponse
import java.text.SimpleDateFormat
import java.util.Locale

fun TimeStampDto.toDomain(): TimeSlot {
    return TimeSlot(
        startTime = startTime,
        endTime = endTime
    )
}

fun AvailableTimesResponseDto.toDomain(): AvailableTimesResponse {
    return AvailableTimesResponse(
        success = success,
        message = message,
        availableTimeSlots = data.timeStamps.map { it.toDomain() },
    )
}

fun TimeSlot.toTimeSlot() = TimeSlot(
    startTime = this.startTime,
    endTime = this.endTime,
    isAvailable = this.isAvailable,
    isSelected = this.isSelected
)

fun formatTime(time: String): String {
    // Define the input and output time formats
    val inputFormatter = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    val outputFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())

    // Parse the input time and format it to the desired output
    val parsedTime = inputFormatter.parse(time)
    return outputFormatter.format(parsedTime)
}