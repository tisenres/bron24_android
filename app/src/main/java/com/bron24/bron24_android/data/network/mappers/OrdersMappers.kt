package com.bron24.bron24_android.data.network.mappers

import android.location.Address
import com.bron24.bron24_android.data.network.dto.orders.AddressDto
import com.bron24.bron24_android.data.network.dto.orders.OrderDetailsDto
import com.bron24.bron24_android.data.network.dto.orders.OrderDto
import com.bron24.bron24_android.data.network.dto.orders.TimeSlotDto
import com.bron24.bron24_android.domain.entity.order.Order
import com.bron24.bron24_android.domain.entity.order.OrderAddress
import com.bron24.bron24_android.domain.entity.order.OrderDetails
import com.bron24.bron24_android.domain.entity.order.OrderStatus
import com.bron24.bron24_android.domain.entity.order.TimeSlot
import com.bron24.bron24_android.helper.util.formatPrice
import com.bron24.bron24_android.helper.util.formatISODateTimeToHourString

fun OrderDto.toDomainModel(): Order = Order(
    id  =id,
    timeSlot =timeSlot,
    bookingDate = bookingDate,
    sector = sector,
    status = status,
    hours = hours,
    orderId = orderId,
    payment = payment,
    user = user,
    venueId = venueId,
    previewImage = previewImage,
    statusName = statusName,
    venueName = venueName,
)
fun OrderDetailsDto.toDomainModel(): OrderDetails = OrderDetails(
    id  =id,
    timeSlot =timeSlot,
    bookingDate = bookingDate,
    sector = sector,
    status = status,
    hours = hours,
    phoneNumber1= phoneNumber1,
    phoneNumber2 = phoneNumber2,
    orderId = orderId,
    payment = payment,
    cost = cost,
    user = user,
    address = address.toDomain(),
    venueId = venueId,
    statusName = statusName,
    venueName = venueName,
    latitude = latitude,
    longitude = longitude,
    distance = distance
)


fun List<OrderDto>.toDomainModel(): List<Order> {
    return this.map { it.toDomainModel() }
}

fun AddressDto.toDomain():OrderAddress = OrderAddress(
    id = id,
    addressName = addressName,
    district = district,
    closestMetroStation = closestMetroStation
)
