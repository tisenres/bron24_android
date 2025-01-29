package com.bron24.bron24_android.screens.menu_pages.orders_page

import android.util.Log
import androidx.compose.animation.fadeIn
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bron24.bron24_android.components.items.OrdersType
import com.bron24.bron24_android.domain.usecases.orders.GetOrdersByStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject


@HiltViewModel
class OrdersPageVM @Inject constructor(
    private val getOrdersByStatusUseCase: GetOrdersByStatusUseCase,
    private val direction:OrdersPageContract.Direction
) : ViewModel(), OrdersPageContract.ViewModel {
    override fun onDispatchers(intent: OrdersPageContract.Intent): Job = intent{
        when(intent){
            OrdersPageContract.Intent.ClickHistory -> {
                reduce { state.copy(isLoading = true, selected = OrdersType.HISTORY) }
                delay(200)
                reduce { state.copy(isLoading = false) }
            }
            OrdersPageContract.Intent.ClickUpcoming -> {
                reduce { state.copy(isLoading = true, selected = OrdersType.UPCOMING) }
                delay(200)
                reduce { state.copy(isLoading = false) }
            }
            is OrdersPageContract.Intent.ClickItemOrder -> {
                direction.moveToInfo(intent.orderId)
            }
            is OrdersPageContract.Intent.Refresh -> {
                getOrdersByStatusUseCase.invoke()
                    .onStart { reduce { state.copy(refresh = true) } }
                    .onEach { reduce { state.copy(inProcess = it.first, history = it.second) } }
                    .onCompletion { reduce { state.copy(isLoading = false, refresh = false)} }
                    .launchIn(viewModelScope)
            }
        }
    }
    private fun upComing():Job = intent{
        reduce { state.copy(selected = OrdersType.UPCOMING) }
        getOrdersByStatusUseCase.invoke()
            .onEach { reduce { state.copy(inProcess = it.first, history = it.second) } }
            .onCompletion { reduce { state.copy(isLoading = false, refresh = false)} }
            .launchIn(viewModelScope)
    }

    override fun initData():Job = intent{
        upComing()
    }

    private fun postSideEffect(message: String) {
        intent {
            postSideEffect(OrdersPageContract.SideEffect(message))
        }
    }

    override val container =
        container<OrdersPageContract.UIState, OrdersPageContract.SideEffect>(OrdersPageContract.UIState())
}
