package com.bron24.bron24_android.domain.entity.booking

data class Booking(
    val id: String = "",
    val venueId: Int,
    val userId: String,
    val startTime: Long,
    val endTime: Long,
    val status: BookingStatus = BookingStatus.PENDING,
    val stadiumPart: String
)