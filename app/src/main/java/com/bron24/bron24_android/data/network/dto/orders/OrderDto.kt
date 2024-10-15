package com.bron24.bron24_android.data.network.dto.orders

data class OrdersResponse(
    val orders: List<OrdersDto>
)

data class OrdersDto(
    val id: Long,
    val title: String,
    val price: Int,
    val date: String,
    val time: String,
    val location: String,
    val status: String // This will contain either "UPCOMING" or "HISTORY"
)
