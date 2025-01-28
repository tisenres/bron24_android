package com.bron24.bron24_android.data.network.dto.orders


import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class OrderDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("time_slot")
    val timeSlot: TimeSlotDto,
    @SerializedName("booking_date")
    val bookingDate: String,
    @SerializedName("sector")
    val sector: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("hours")
    val hours: String,
    @SerializedName("order_id")
    val orderId: String,
    @SerializedName("payment")
    val payment: String,
    @SerializedName("user")
    val user: Int,
    @SerializedName("venue_id")
    val venueId: Int,
    @SerializedName("preview_image")
    val previewImage: String,
    @SerializedName("status_name")
    val statusName:String,
    @SerializedName("venue_name")
    val venueName:String
) : Parcelable

data class OrderDetailsDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("time_slot")
    val timeSlot: TimeSlotDto,
    @SerializedName("booking_date")
    val bookingDate: String,
    @SerializedName("address")
    val address:AddressDto,
    @SerializedName("sector")
    val sector: String,
    @SerializedName("cost")
    val cost:String,
    @SerializedName("status")
    val status: String,
    @SerializedName("hours")
    val hours: String,
    @SerializedName("phone_number_1")
    val phoneNumber1:String,
    @SerializedName("phone_number_2")
    val phoneNumber2: String,
    @SerializedName("order_id")
    val orderId: String,
    @SerializedName("payment")
    val payment: String,
    @SerializedName("user")
    val user: Int,
    @SerializedName("venue_id")
    val venueId: Int,
    @SerializedName("status_name")
    val statusName:String,
    @SerializedName("venue_name")
    val venueName:String,
    @SerializedName("latitude")
    val latitude:Double,
    @SerializedName("longitude")
    val longitude:Double,
    @SerializedName("distance")
    val distance:Double
)

@Parcelize
@Keep
data class TimeSlotDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("start_time")
    val startTime: String,
    @SerializedName("end_time")
    val endTime: String,
    @SerializedName("venue_id")
    val venueId: Int
) : Parcelable

@Parcelize
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
) : Parcelable