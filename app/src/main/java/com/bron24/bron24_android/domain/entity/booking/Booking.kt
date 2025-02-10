package com.bron24.bron24_android.domain.entity.booking

data class Booking(
    val venueId: Int,
    var orderId: Int,
    val phoneNumber: String,
    val bookingDate: String,
    val sector: String,
    val timeSlots: List<TimeSlot>,
    var firstName: String? = "",
    var lastName: String? = "",
    var venueName: String? = "",
    var venueAddress: String? = "",
    var totalHours: Double = 0.0,
    var cost: String = "",
    val status: BookingStatus = BookingStatus.INPROCESS,
    var orderIds: List<String> = emptyList()
)