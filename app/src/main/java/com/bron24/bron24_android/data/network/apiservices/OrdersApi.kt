package com.bron24.bron24_android.data.network.apiservices

import com.bron24.bron24_android.data.network.dto.orders.RequestCancelOrderDto
import com.bron24.bron24_android.data.network.dto.orders.ResponseCancelOrderDto
import com.bron24.bron24_android.data.network.dto.orders.ResponseOrderDetailsDto
import com.bron24.bron24_android.data.network.dto.orders.ResponseOrdersDto
import com.bron24.bron24_android.domain.entity.order.OrderStatus
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface OrdersApi {
    @GET("/api/v1/orders/")
    suspend fun getOrders(): ResponseOrdersDto

    @GET("/api/v1/orders/{id}")
    suspend fun getOrderDetails(@Path("id") orderId: Int): ResponseOrderDetailsDto

    @GET("/api/v1/orders/")
    suspend fun getOrdersByStatus(@Query("status") status:String):ResponseOrdersDto

    @POST("/api/v1/orders/cancel")
    suspend fun cancelOrder(@Body body: RequestCancelOrderDto): ResponseCancelOrderDto
}
