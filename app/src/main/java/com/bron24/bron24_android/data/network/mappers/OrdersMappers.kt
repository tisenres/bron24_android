package com.bron24.bron24_android.data.network.mappers

import com.bron24.bron24_android.data.network.dto.orders.OrderDto
import com.bron24.bron24_android.domain.entity.order.Order
import com.bron24.bron24_android.domain.entity.order.OrderAddress
import com.bron24.bron24_android.domain.entity.order.OrderStatus
import com.bron24.bron24_android.domain.entity.order.TimeSlot
import com.bron24.bron24_android.helper.extension.DateTimeFormatter
import com.bron24.bron24_android.helper.extension.formatPrice

fun OrderDto.toDomainModel(): Order {
    return Order(
        id = this.id,
        venueName = this.venueName ?: "Venue Name",
        venueId = this.venueId,
        timeSlot = TimeSlot(
            startTime = DateTimeFormatter.formatISODateTimeToHourString(this.timeSlot.startTime),
            endTime = DateTimeFormatter.formatISODateTimeToHourString(this.timeSlot.endTime),
            isAvailable = this.timeSlot.isAvailable
        ),
        bookingDate = this.bookingDate,
        sector = this.sector,
        status = when (this.status) {
            "INPROCESS" -> OrderStatus.IN_PROCESS
            "CANCELLED" -> OrderStatus.CANCELLED
            else -> OrderStatus.CANCELLED
        },
        cost = this.cost.formatPrice(),
        hours = this.hours,
        bookingCreation = this.bookingCreation,
        phoneNumber1 = this.phoneNumber1,
        phoneNumber2 = this.phoneNumber2,
        orderId = this.orderId,
        address = OrderAddress(
            id = this.address.id,
            addressName = this.address.addressName,
            district = this.address.district,
            closestMetroStation = this.address.closestMetroStation
        ),
        previewImage = this.previewImage,
        lat = this.latitude,
        lon = this.longitude
    )
}

fun List<OrderDto>.toDomainModel(): List<Order> {
    return this.map { it.toDomainModel() }
}
