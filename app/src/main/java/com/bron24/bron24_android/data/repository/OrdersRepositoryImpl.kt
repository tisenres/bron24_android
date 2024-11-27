package com.bron24.bron24_android.data.repository

import com.bron24.bron24_android.data.network.apiservices.OrdersApi
import com.bron24.bron24_android.data.network.dto.orders.RequestCancelOrderDto
import com.bron24.bron24_android.data.network.dto.orders.ResponseCancelOrderDto
import com.bron24.bron24_android.data.network.dto.orders.ResponseOrderDetailsDto
import com.bron24.bron24_android.data.network.dto.orders.ResponseOrdersDto
import com.bron24.bron24_android.domain.repository.OrdersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OrdersRepositoryImpl @Inject constructor(
    private val api: OrdersApi
) : OrdersRepository {
    override suspend fun getOrders(): Flow<ResponseOrdersDto> = flow {
        val response = api.getOrders()
        emit(response) // assuming the endpoint returns OrdersResponse
    }

    override suspend fun getOrderDetails(id: Long): Flow<ResponseOrderDetailsDto> = flow {
        val response = api.getOrderDetails(id)
        emit(response)
    }

    override suspend fun cancelOrder(id: Long): Flow<ResponseCancelOrderDto> = flow {
        val response = api.cancelOrder(RequestCancelOrderDto(id))
        emit(response)
    }

}
