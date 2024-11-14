package com.bron24.bron24_android.data.network.dto.orders


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ResponseOrderDto(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<OrderDto>
)

@Keep
data class OrderDto(
    @SerializedName("id")
    val id: Long,
    @SerializedName("venue_name")
    val venueName: String?,
    @SerializedName("time_slot")
    val timeSlot: TimeSlotDto,
    @SerializedName("booking_date")
    val bookingDate: String,
    @SerializedName("sector")
    val sector: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("cost")
    val cost: String,
    @SerializedName("hours")
    val hours: String,
    @SerializedName("booking_creation")
    val bookingCreation: String,
    @SerializedName("phone_number_1")
    val phoneNumber1: String,
    @SerializedName("phone_number_2")
    val phoneNumber2: String?,
    @SerializedName("order_id")
    val orderId: String,
    @SerializedName("user")
    val user: Int,
    @SerializedName("venue_id")
    val venueId: Int,
    @SerializedName("address")
    val address: AddressDto,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("preview_image")
    val previewImage: String
)

@Keep
data class TimeSlotDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("booking_date")
    val bookingDate: String,
    @SerializedName("start_time")
    val startTime: String,
    @SerializedName("end_time")
    val endTime: String,
    @SerializedName("is_available")
    val isAvailable: Boolean,
    @SerializedName("venue_id")
    val venueId: Int
)


@Keep
data class AddressDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("address_name")
    val addressName: String,
    @SerializedName("district")
    val district: String,
    @SerializedName("closest_metro_station")
    val closestMetroStation: String
)