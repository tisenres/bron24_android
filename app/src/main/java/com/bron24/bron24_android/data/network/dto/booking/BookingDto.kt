package com.bron24.bron24_android.data.network.dto.booking

import com.google.gson.annotations.SerializedName

data class RequestBookingDto(
//    @SerializedName("user") val user: String,
    @SerializedName("venue_id") val venueId: Int,
    @SerializedName("booking_date") val bookingDate: String,
    @SerializedName("time_slot") val timeSlot: List<String>,
    @SerializedName("sector") val sector: String,
    @SerializedName("status") val status: String = "INPROCESS",
    @SerializedName("phone_number_2") val phoneNumber: String = "",
    @SerializedName("payment") val payment:String = "cash"
)

data class ResponseBookingDto(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: BookingData
)

data class BookingData(
    @SerializedName("user") val user: User?,
    @SerializedName("venue_id") val venue: Venue?,
    @SerializedName("time_slot") val timeSlots: List<TimeSlot>,
    @SerializedName("date") val date:String,
    @SerializedName("cost") val cost: Int?,
    @SerializedName("hours") val hours: String,
    @SerializedName("sector") val sector: String?,
    @SerializedName("is_saved") val isSaved: Boolean?,
    @SerializedName("order_id") val orderIds: List<String>
)

data class User(
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("phone_number") val phoneNumber: String
)

data class Venue(
    @SerializedName("venue_name") val venueName: String,
    @SerializedName("venue_address") val venueAddress: String
)

data class TimeSlot(
    @SerializedName("start_time") val startTime: String,
    @SerializedName("end_time") val endTime: String,
    @SerializedName("hours") val hours: Int
)