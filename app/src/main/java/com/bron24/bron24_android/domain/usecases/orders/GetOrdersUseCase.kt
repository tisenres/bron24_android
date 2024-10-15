package com.bron24.bron24_android.domain.usecases.orders

import com.bron24.bron24_android.data.network.mappers.toDomainModel
import com.bron24.bron24_android.domain.entity.order.Order
import com.bron24.bron24_android.domain.repository.OrdersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetOrdersUseCase @Inject constructor(
    private val repository: OrdersRepository
) {
    suspend fun execute(): Flow<List<Order>> {
        return repository.getOrders().map { dtoList ->
            dtoList.toDomainModel() // Use the mapper to convert DTO to domain model
        }
    }
}
