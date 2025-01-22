package com.bron24.bron24_android.data.network.mappers

import com.bron24.bron24_android.data.network.dto.orders.OrderDto
import com.bron24.bron24_android.data.network.dto.orders.TimeSlotDto
import com.bron24.bron24_android.domain.entity.order.Order
import com.bron24.bron24_android.domain.entity.order.OrderAddress
import com.bron24.bron24_android.domain.entity.order.OrderStatus
import com.bron24.bron24_android.domain.entity.order.TimeSlot
import com.bron24.bron24_android.helper.extension.DateTimeFormatter
import com.bron24.bron24_android.helper.extension.formatPrice

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
    venueName = venueName
)

fun List<OrderDto>.toDomainModel(): List<Order> {
    return this.map { it.toDomainModel() }
}
