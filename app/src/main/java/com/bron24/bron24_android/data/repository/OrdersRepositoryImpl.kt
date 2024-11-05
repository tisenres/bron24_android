package com.bron24.bron24_android.data.repository

import com.bron24.bron24_android.data.network.apiservices.OrdersApi
import com.bron24.bron24_android.data.network.dto.orders.OrdersDto
import com.bron24.bron24_android.domain.repository.OrdersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OrdersRepositoryImpl @Inject constructor(
    private val api: OrdersApi
) : OrdersRepository {
    override suspend fun getOrders(): Flow<List<OrdersDto>> = flow {
        val response = api.getOrders()
//        emit(response.orders) // assuming the endpoint returns OrdersResponse
    }
}
