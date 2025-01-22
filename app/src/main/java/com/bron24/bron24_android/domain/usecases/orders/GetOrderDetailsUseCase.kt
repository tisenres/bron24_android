package com.bron24.bron24_android.domain.usecases.orders

import com.bron24.bron24_android.data.network.mappers.toDomainModel
import com.bron24.bron24_android.domain.entity.order.Order
import com.bron24.bron24_android.domain.repository.OrdersRepository
import com.bron24.bron24_android.domain.repository.VenueRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetOrderDetailsUseCase @Inject constructor(
    private val ordersRepository: OrdersRepository,
    private val venueRepository: VenueRepository,
) {
    suspend fun execute(orderId: Long): Flow<Order> {
        return ordersRepository.getOrderDetails(orderId).map { data ->
            val images = venueRepository.getVenuePictures(data.data.venueId)
            data.data.toDomainModel()
        }
    }

    suspend fun cancelOrder(orderId: Long): Flow<Boolean> {
        return ordersRepository.cancelOrder(orderId).map {
            it.success
        }
    }
}
