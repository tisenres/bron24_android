package com.bron24.bron24_android.domain.entity.booking

import com.bron24.bron24_android.domain.entity.venue.Address

data class Booking(
    val id: String = "",
    val venueId: Int,
    val userId: String,
    val startTime: Long,
    val endTime: Long,
    val venueName: String,
    val address: Address,
    val date: Long,
    val status: BookingStatus = BookingStatus.PENDING,
    val stadiumPart: String,
    val fullName: String,
    val firstNumber: String,
    val secondNumber: String?,
    val totalPrice: String
)