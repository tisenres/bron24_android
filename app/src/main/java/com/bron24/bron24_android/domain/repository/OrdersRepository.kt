package com.bron24.bron24_android.domain.repository

import com.bron24.bron24_android.data.network.dto.orders.ResponseCancelOrderDto
import com.bron24.bron24_android.data.network.dto.orders.ResponseOrderDetailsDto
import com.bron24.bron24_android.data.network.dto.orders.ResponseOrdersDto
import kotlinx.coroutines.flow.Flow

interface OrdersRepository {
    suspend fun getOrders(): Flow<ResponseOrdersDto>

    suspend fun getOrderDetails(id: Long): Flow<ResponseOrderDetailsDto>

    suspend fun cancelOrder(id: Long): Flow<ResponseCancelOrderDto>
}