package com.bron24.bron24_android.domain.entity.user

import com.bron24.bron24_android.domain.entity.venue.Venue


data class Booking(
    val bookingId: String,
    val user: User,
    val venue: Venue,
//    val bookingTime: LocalDateTime,
//    val duration: Duration,
//    val status: BookingStatus
)