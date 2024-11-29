package com.bron24.bron24_android.domain.entity.order

import androidx.annotation.Keep

@Keep
data class Order(
    val id: Long,
    val venueName: String,
    val venueId: Int,
    val timeSlot: TimeSlot,
    val bookingDate: String,
    val sector: String,
    val status: OrderStatus,
    val cost: String,
    val hours: String,
    val bookingCreation: String,
    val phoneNumber1: String,
    val phoneNumber2: String?,
    val orderId: String,
    val address: OrderAddress,
    val previewImage: String,
    var images: List<String>? = null,
    val lat: Double,
    val lon: Double
)

@Keep
data class TimeSlot(
    val startTime: String,
    val endTime: String,
    val isAvailable: Boolean,
)

@Keep
enum class OrderStatus {
    IN_PROCESS, COMPLETED, CANCELLED
}

@Keep
data class OrderAddress(
    val id: Int,
    val addressName: String,
    val district: String,
    val closestMetroStation: String
)

