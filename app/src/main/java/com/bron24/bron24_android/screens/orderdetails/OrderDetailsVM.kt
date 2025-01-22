package com.bron24.bron24_android.screens.orderdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bron24.bron24_android.domain.entity.order.Order
import com.bron24.bron24_android.domain.usecases.orders.GetOrderDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

class OrderDetailsVM {
}


//sealed class OrderDetailState {
//    data object Loading : OrderDetailState()
//    data class Success(
//        val data: Order,
//        val isCancelling: Boolean = false,
//        val isCanceled: Boolean = false
//    ) : OrderDetailState()
//
//    data class Error(val message: String) : OrderDetailState()
//}
//
//@HiltViewModel
//class OrderDetailsViewModel @Inject constructor(
//    private val orderDetailsUseCase: GetOrderDetailsUseCase
//) : ViewModel() {
//
//    private val _orderState = MutableStateFlow<OrderDetailState>(OrderDetailState.Loading)
//    val orderState: StateFlow<OrderDetailState> = _orderState.asStateFlow()
//
//    fun fetchOrder(orderId: Int) {
//        viewModelScope.launch {
//            try {
//                orderDetailsUseCase.execute(orderId = orderId)
//                    .catch {
//                        _orderState.value = OrderDetailState.Error(it.message ?: "Unknown error")
//                    }
//                    .collect { order: Order ->
//                        _orderState.value = OrderDetailState.Success(order)
//                    }
//            } catch (e: Exception) {
//                _orderState.value = OrderDetailState.Error(e.message ?: "Unknown error")
//            }
//        }
//    }
//
//    fun cancelOrder(orderId: Long) {
//        viewModelScope.launch {
//            val currentState = _orderState.value
//            if (currentState is OrderDetailState.Success) {
//                _orderState.value = currentState.copy(isCancelling = true, isCanceled = false)
//                try {
//                    orderDetailsUseCase.cancelOrder(orderId).collect { isCancelled ->
//                        _orderState.value = currentState.copy(
//                            isCancelling = false,
//                            isCanceled = isCancelled
//                        )
//                    }
//                } catch (e: Exception) {
//                    _orderState.value = currentState.copy(isCancelling = false, isCanceled = false)
//                }
//            }
//        }
//    }
//
//}