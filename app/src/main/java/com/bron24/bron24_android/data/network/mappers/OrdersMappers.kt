package com.bron24.bron24_android.data.network.mappers

import com.bron24.bron24_android.data.network.dto.orders.OrdersDto
import com.bron24.bron24_android.domain.entity.order.Order

fun OrdersDto.toDomainModel(): Order {
    return Order(
        id = this.id,
        title = this.title,
        price = this.price,
        date = this.date,
        time = this.time,
        location = this.location,
        status = when (this.status) {
            "UPCOMING" -> "In process"
            "HISTORY" -> "Completed"
            else -> "Unknown"
        }
    )
}

fun List<OrdersDto>.toDomainModel(): List<Order> {
    return this.map { it.toDomainModel() }
}
