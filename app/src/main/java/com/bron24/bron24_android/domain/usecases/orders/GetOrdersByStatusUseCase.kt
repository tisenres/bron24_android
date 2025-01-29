package com.bron24.bron24_android.domain.usecases.orders

import com.bron24.bron24_android.data.network.dto.orders.ResponseOrdersDto
import com.bron24.bron24_android.data.network.mappers.toDomainModel
import com.bron24.bron24_android.domain.entity.order.Order
import com.bron24.bron24_android.domain.repository.OrdersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetOrdersByStatusUseCase @Inject constructor(
    private val ordersRepository: OrdersRepository
) {
    operator fun invoke(): Flow<Pair<List<Order>, List<Order>>> =
        combine(
            ordersRepository.getOrdersByStatus("INPROCESS"),
            ordersRepository.getOrdersByStatus("history")
        ) { inProcessOrders, historyOrders ->
            val inProcessList = inProcessOrders.data?.map { it.toDomainModel() } ?: emptyList()
            val historyList = historyOrders.data?.map { it.toDomainModel() } ?: emptyList()
            Pair(inProcessList, historyList)
        }

}