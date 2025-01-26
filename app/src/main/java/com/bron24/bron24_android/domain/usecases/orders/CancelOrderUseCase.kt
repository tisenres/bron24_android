package com.bron24.bron24_android.domain.usecases.orders

import com.bron24.bron24_android.domain.repository.OrdersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CancelOrderUseCase @Inject constructor(
    private val ordersRepository: OrdersRepository
) {
    operator fun invoke(id:Int): Flow<Result<Unit>> = flow {

    }
}