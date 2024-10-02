package com.bron24.bron24_android.data.network.mappers

import android.os.Build
import androidx.annotation.RequiresApi
import com.bron24.bron24_android.data.network.dto.booking.AvailableTimesResponseDto
import com.bron24.bron24_android.data.network.dto.booking.ResponseBookingDto
import com.bron24.bron24_android.data.network.dto.booking.TimeStampDto
import com.bron24.bron24_android.domain.entity.booking.TimeSlot
import com.bron24.bron24_android.domain.entity.booking.AvailableTimesResponse
import com.bron24.bron24_android.domain.entity.booking.Booking
import com.bron24.bron24_android.domain.entity.booking.BookingStatus
import com.bron24.bron24_android.domain.entity.booking.FinalBookingTimeSlot
import com.bron24.bron24_android.domain.entity.venue.Address
import java.time.LocalTime
import java.time.format.DateTimeFormatter

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

//fun ResponseBookingDto.toDomain(): Booking {
//    val bookingData = this.data
//
//    return Booking(
//        venueId = 3,
//        bookingDate = bookingData,  // Adjust if necessary for date handling
//        sector = bookingData.sector,
//        timeSlots = bookingData.timeSlots.map { "${it.startTime} - ${it.endTime}" },  // Map time slots to a list of formatted strings
//        cost = bookingData.cost.toDouble(),  // Convert cost to Double
//        status = BookingStatus.INPROCESS,
//
//    )
//}

//fun TimeSlot.mapToFinalTimeSlots() : FinalBookingTimeSlot {
//    val hours = calculateHours(this.startTime, this.endTime)
//    return FinalBookingTimeSlot(
//        startTime = this.startTime,
//        endTime = this.endTime,
//        hours = hours
//}



@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
fun formatTime(time: String): String {
    val inputFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    val outputFormatter = DateTimeFormatter.ofPattern("HH:mm")
    val parsedTime = LocalTime.parse(time, inputFormatter)
    return parsedTime.format(outputFormatter)
}