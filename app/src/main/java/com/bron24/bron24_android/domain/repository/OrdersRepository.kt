package com.bron24.bron24_android.domain.repository

import com.bron24.bron24_android.data.network.dto.orders.ResponseCancelOrderDto
import com.bron24.bron24_android.data.network.dto.orders.ResponseOrderDetailsDto
import com.bron24.bron24_android.data.network.dto.orders.ResponseOrdersDto
import kotlinx.coroutines.flow.Flow

interface OrdersRepository {
    fun getOrders(): Flow<ResponseOrdersDto>

    fun getOrderDetails(id: Long): Flow<ResponseOrderDetailsDto>

    fun cancelOrder(id: Long): Flow<ResponseCancelOrderDto>

    fun getOrdersByStatus(status:String):Flow<ResponseOrdersDto>
}