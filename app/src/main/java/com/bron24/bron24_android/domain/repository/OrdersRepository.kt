package com.bron24.bron24_android.domain.repository

import com.bron24.bron24_android.data.network.dto.orders.ResponseOrderDto
import kotlinx.coroutines.flow.Flow

interface OrdersRepository {
    suspend fun getOrders(): Flow<ResponseOrderDto>
}