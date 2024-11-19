package com.bron24.bron24_android.screens.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bron24.bron24_android.domain.entity.order.Order
import com.bron24.bron24_android.domain.entity.order.OrderStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data object Empty : UiState<Nothing>()
}

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val ordersModel: OrdersModel
) : ViewModel() {

    private val _orders = MutableStateFlow<UiState<List<Order>>>(UiState.Loading)
    val orders: StateFlow<UiState<List<Order>>> = _orders

    val upcomingOrders = orders.map { state ->
        (state as? UiState.Success)?.data?.filter { it.status == OrderStatus.IN_PROCESS }.orEmpty()
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val historyOrders = orders.map { state ->
        (state as? UiState.Success)?.data?.filter { it.status == OrderStatus.CANCELLED }.orEmpty()
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init {
        fetchOrders()
    }

    private fun fetchOrders() {
        viewModelScope.launch {
            try {
                ordersModel.getOrders().collect { ordersList ->
                    _orders.value =
                        if (ordersList.isEmpty()) UiState.Empty else UiState.Success(ordersList)
                }
            } catch (e: Exception) {
            }
        }
    }
}
