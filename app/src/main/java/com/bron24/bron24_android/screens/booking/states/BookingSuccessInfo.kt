package com.bron24.bron24_android.screens.booking.states

import com.bron24.bron24_android.domain.entity.booking.TimeSlot

data class BookingSuccessInfo(
    val orderId: String,
    val venueName: String,
    val date: String,
    val timeSlots: List<TimeSlot>,
    val sector: String,
)