package com.bron24.bron24_android.domain.usecases.orders

import com.bron24.bron24_android.data.network.dto.orders.ResponseOrdersDto
import com.bron24.bron24_android.data.network.mappers.toDomainModel
import com.bron24.bron24_android.domain.entity.order.Order
import com.bron24.bron24_android.domain.repository.OrdersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetOrdersByStatusUseCase @Inject constructor(
    private val ordersRepository: OrdersRepository
) {
    operator fun invoke(status:String):Flow<List<Order>>{
        return ordersRepository.getOrdersByStatus(status).map { it.data.toDomainModel() }
    }
}