package com.bron24.bron24_android.screens.orders

import com.bron24.bron24_android.domain.entity.order.Order
import com.bron24.bron24_android.domain.usecases.orders.GetOrdersUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OrdersModel @Inject constructor(
    private val getOrdersUseCase: GetOrdersUseCase
){
    suspend fun getOrders(): Flow<List<Order>> {
        return getOrdersUseCase.execute()
    }
}