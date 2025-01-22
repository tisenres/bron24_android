package com.bron24.bron24_android.data.network.mappers

import com.bron24.bron24_android.common.VenueOrderInfo
import com.bron24.bron24_android.data.network.dto.booking.AvailableTimesResponseDto
import com.bron24.bron24_android.data.network.dto.booking.RequestBookingDto
import com.bron24.bron24_android.data.network.dto.booking.ResponseBookingDto
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

fun VenueOrderInfo.toNetworkModel(): RequestBookingDto {
    return RequestBookingDto(
        venueId = venueId,
        bookingDate = date,
        sector = sector,
        timeSlot = timeSlots.map { "${it.startTime}-${it.endTime}" },
        status = BookingStatus.INPROCESS.name,
        phoneNumber = secondPhone?.substring(1)?:""
    )
}

fun ResponseBookingDto.toDomain():VenueOrderInfo = VenueOrderInfo(
    venueId = 0,
    venueName = data.venue?.venueName?:"",
    date = data.date,
    sector = data.sector?:"",
    resTimeSlot = data.timeSlots.map { "${it.startTime}-${it.endTime}" },
    secondPhone = "",
    orderId = data.orderIds,
    success = if(success) true else false,
    timeSlots = emptyList()
)

