package com.bron24.bron24_android.domain.usecases.orders

import androidx.compose.ui.input.key.Key.Companion.U
import com.bron24.bron24_android.domain.repository.OrdersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CancelOrderUseCase @Inject constructor(
    private val ordersRepository: OrdersRepository
) {
    operator fun invoke(id:Int): Flow<Result<Unit>> = flow {
        ordersRepository.cancelOrder(id).collect{
            if(it.success){
                emit(Result.success(Unit))
            }else{
                emit(Result.failure(Exception(it.message)))
            }
        }
    }
}