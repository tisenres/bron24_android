package com.bron24.bron24_android.screens.menu_pages.orders_page

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bron24.bron24_android.components.items.OrdersType
import com.bron24.bron24_android.domain.usecases.orders.GetOrdersByStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
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
                reduce { state.copy(isLoading = true) }
                history()
            }
            OrdersPageContract.Intent.ClickUpcoming -> {
                reduce { state.copy(isLoading = true) }
                upComing()
            }

            is OrdersPageContract.Intent.ClickItemOrder -> {
                direction.moveToInfo(intent.orderId)
            }
            is OrdersPageContract.Intent.Refresh -> {
                when(intent.type){
                    OrdersType.UPCOMING -> {
                        reduce { state.copy(refresh = true) }
                        upComing()
                    }
                    OrdersType.HISTORY -> {
                        reduce { state.copy(refresh = true) }
                        history()
                    }
                }
            }
        }
    }
    private fun upComing():Job = intent{
        Log.d("AAA", "upComing: up comming")
        getOrdersByStatusUseCase.invoke("INPROCESS")
            .onEach { reduce { state.copy(itemData = it, selected = OrdersType.UPCOMING) } }
            .onCompletion { reduce { state.copy(isLoading = false, refresh = false)} }
            .launchIn(viewModelScope)
    }
    private fun history():Job = intent{
        getOrdersByStatusUseCase.invoke("history")
            .onEach { reduce { state.copy(itemData = it, selected = OrdersType.HISTORY) } }
            .onCompletion { reduce { state.copy(isLoading = false, refresh = false) }}
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
