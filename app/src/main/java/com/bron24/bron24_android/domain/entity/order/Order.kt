package com.bron24.bron24_android.domain.entity.order

import androidx.annotation.Keep
import com.bron24.bron24_android.data.network.dto.orders.TimeSlotDto
import com.google.gson.annotations.SerializedName

@Keep
data class Order(
    val id: Int,
    val timeSlot: TimeSlotDto,
    val bookingDate: String,
    val sector: String,
    val status: OrderStatus,
    val hours: String,
    val orderId: String,
    val payment: String,
    val user: Int,
    val venueId: Int,
    val previewImage: String,
    val statusName: String,
    val venueName: String,
)

@Keep
data class OrderDetails(
    val id: Int,
    val timeSlot: TimeSlotDto,
    val bookingDate: String,
    val sector: String,
    val status: String,
    val hours: String,
    val cost: String,
    val address: OrderAddress,
    val phoneNumber1: String,
    val phoneNumber2: String,
    val orderId: String,
    val payment: String,
    val user: Int,
    val venueId: Int,
    val statusName: String,
    val venueName: String,
    val latitude: Double,
    val longitude: Double,
    val distance: Double
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

