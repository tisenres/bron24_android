package com.bron24.bron24_android.data.network.mappers

import com.bron24.bron24_android.data.network.dto.booking.AvailableTimesResponseDto
import com.bron24.bron24_android.data.network.dto.booking.RequestBookingDto
import com.bron24.bron24_android.data.network.dto.booking.TimeStampDto
import com.bron24.bron24_android.domain.entity.booking.AvailableTimesResponse
import com.bron24.bron24_android.domain.entity.booking.Booking
import com.bron24.bron24_android.domain.entity.booking.BookingStatus
import com.bron24.bron24_android.domain.entity.booking.TimeSlot

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

fun Booking.toNetworkModel(): RequestBookingDto {
    return RequestBookingDto(
        user = phoneNumber,
        venueId = venueId,
        bookingDate = bookingDate,
        sector = sector,
//        timeSlot = timeSlots.map { "${formatTime(it.startTime)}-${formatTime(it.endTime)}" },
        timeSlot = timeSlots.map { "${it.startTime}-${it.endTime}" },
        status = BookingStatus.INPROCESS.name
    )
}

fun TimeSlot.toTimeSlot() = TimeSlot(
    startTime = this.startTime,
    endTime = this.endTime,
    isAvailable = this.isAvailable,
    isSelected = this.isSelected
)

//fun formatTime(time: String): String {
//     Define the input and output time formats
//    val inputFormatter = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
//    val outputFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())
//
//     Parse the input time and format it to the desired output
//    val parsedTime = inputFormatter.parse(time)
//    return outputFormatter.format(parsedTime)
//}