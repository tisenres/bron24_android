package com.bron24.bron24_android.domain.usecases.orders

import com.bron24.bron24_android.data.network.dto.orders.ResponseOrdersDto
import com.bron24.bron24_android.domain.repository.OrdersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOrdersByStatusUseCase @Inject constructor(
    private val ordersRepository: OrdersRepository
) {
    operator fun invoke(status:String):Flow<ResponseOrdersDto> = ordersRepository.getOrders()
}