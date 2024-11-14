package com.bron24.bron24_android.data.repository

import com.bron24.bron24_android.data.network.apiservices.OrdersApi
import com.bron24.bron24_android.data.network.dto.orders.ResponseOrderDto
import com.bron24.bron24_android.domain.repository.OrdersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OrdersRepositoryImpl @Inject constructor(
    private val api: OrdersApi
) : OrdersRepository {
    override suspend fun getOrders(): Flow<ResponseOrderDto> = flow {
        val response = api.getOrders()
        emit(response) // assuming the endpoint returns OrdersResponse
    }
}
