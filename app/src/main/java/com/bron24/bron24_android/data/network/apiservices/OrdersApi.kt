package com.bron24.bron24_android.data.network.apiservices

import com.bron24.bron24_android.data.network.dto.orders.OrdersResponse
import retrofit2.http.GET

interface OrdersApi {
    @GET("orders")
    suspend fun getOrders(): List<OrdersResponse>
}
