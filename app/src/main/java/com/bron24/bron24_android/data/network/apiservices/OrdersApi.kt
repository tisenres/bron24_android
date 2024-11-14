package com.bron24.bron24_android.data.network.apiservices

import com.bron24.bron24_android.data.network.dto.orders.ResponseOrderDto
import retrofit2.http.GET

interface OrdersApi {
    @GET("/api/v1/orders/")
    suspend fun getOrders(): ResponseOrderDto
}
