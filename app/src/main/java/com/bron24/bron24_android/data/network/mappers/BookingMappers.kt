package com.bron24.bron24_android.data.network.mappers

import com.bron24.bron24_android.data.network.dto.booking.BookingDto
import com.bron24.bron24_android.domain.entity.booking.Booking
import com.bron24.bron24_android.domain.entity.booking.BookingStatus

fun BookingDto.toDomainModel(): Booking {
    return Booking(
        id = id,
        venueId = venueId,
        userId = userId,
        startTime = startTime,
        endTime = endTime,
        status = BookingStatus.valueOf(status)
    )
}

fun Booking.toDto(): BookingDto {
    return BookingDto(
        id = id,
        venueId = venueId,
        userId = userId,
        startTime = startTime,
        endTime = endTime,
        status = status.name
    )
}