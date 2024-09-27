package com.bron24.bron24_android.domain.entity.booking

data class BookingDetails(
    val venueName: String,
    val date: String,
    val time: String,
    val stadiumPart: String,
    val fullName: String,
    val firstNumber: String,
    val secondNumber: String?,
    val totalPrice: String
)