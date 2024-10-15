package com.bron24.bron24_android.screens.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bron24.bron24_android.domain.entity.order.Order
import com.bron24.bron24_android.domain.usecases.orders.GetOrdersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val ordersModel: OrdersModel
) : ViewModel() {

    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders: StateFlow<List<Order>> = _orders

    init {
        fetchOrders()
    }

    private fun fetchOrders() {
        viewModelScope.launch {
            ordersModel.getOrders().collect { ordersList ->
                _orders.value = ordersList
            }
        }
    }
}
