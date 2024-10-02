package com.bron24.bron24_android.domain.entity.booking

import com.bron24.bron24_android.domain.entity.venue.Address

//data class Booking(
//    val id: String,
//    val venueId: Int,
//    val userId: String,
//    val startTime: Long,
//    val endTime: Long,
//    val venueName: String,
//    val address: Address,
//    val date: Long,
//    val status: BookingStatus,
//    val stadiumPart: String,
//    val fullName: String,
//    val firstNumber: String,
//    val secondNumber: String?,
//    val totalPrice: String
//)

data class Booking(
    val venueId: Int,
    val bookingDate: String,  // Consider using LocalDate for a more robust date handling
    val sector: String,
    val timeSlots: List<String>,
    val cost: Double,
    val userName: String,  // You can replace with a User model if needed
    val isSaved: Boolean = false,  // Default value
    val status: BookingStatus = BookingStatus.INPROCESS  // Enum for booking status
)