package com.bron24.bron24_android.data.repository

import com.bron24.bron24_android.data.network.apiservices.OrdersApi
import com.bron24.bron24_android.data.network.dto.orders.RequestCancelOrderDto
import com.bron24.bron24_android.data.network.dto.orders.ResponseCancelOrderDto
import com.bron24.bron24_android.data.network.dto.orders.ResponseOrderDetailsDto
import com.bron24.bron24_android.data.network.dto.orders.ResponseOrdersDto
import com.bron24.bron24_android.domain.repository.OrdersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OrdersRepositoryImpl @Inject constructor(
    private val api: OrdersApi
) : OrdersRepository {
    override fun getOrders(): Flow<ResponseOrdersDto> = flow {
        val response = api.getOrders()
        emit(response) // assuming the endpoint returns OrdersResponse
    }.flowOn(Dispatchers.IO)

    override fun getOrderDetails(id: Int,latitude:Double?,longitude:Double?): Flow<ResponseOrderDetailsDto> = flow {
        val response = api.getOrderDetails(id, latitude = latitude,longitude = longitude)
        emit(response)
    }.flowOn(Dispatchers.IO)

    override fun cancelOrder(id: Int): Flow<ResponseCancelOrderDto> = flow {
        val response = api.cancelOrder(RequestCancelOrderDto(id))
        emit(response)
    }.flowOn(Dispatchers.IO)

    override fun getOrdersByStatus(status: String): Flow<ResponseOrdersDto> = flow {
        emit(api.getOrdersByStatus(status))
    }.flowOn(Dispatchers.IO)

}
