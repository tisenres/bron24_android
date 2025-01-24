package com.bron24.bron24_android.screens.menu_pages.orders_page

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
    private val getOrdersByStatusUseCase: GetOrdersByStatusUseCase
) : ViewModel(), OrdersPageContract.ViewModel {
    override fun onDispatchers(intent: OrdersPageContract.Intent): Job = intent {
        when(intent){
            OrdersPageContract.Intent.ClickHistory -> {
                getOrdersByStatusUseCase.invoke("history")
                    .onStart { reduce { state.copy(isLoading = true) } }
                    .onEach { reduce { state.copy(itemData = it, selected = OrdersType.HISTORY) } }
                    .onCompletion { reduce { state.copy(isLoading = false) } }
                    .launchIn(viewModelScope)
            }
            OrdersPageContract.Intent.ClickUpcoming -> {
                getOrdersByStatusUseCase.invoke("INPROCESS")
                    .onStart { reduce { state.copy(isLoading = true) } }
                    .onEach { reduce { state.copy(itemData = it, selected = OrdersType.UPCOMING) } }
                    .onCompletion { reduce { state.copy(isLoading = false) } }
                    .launchIn(viewModelScope)
            }

            is OrdersPageContract.Intent.ClickItemOrder -> {

            }
        }
    }

    override fun initData(): Job = intent {
        getOrdersByStatusUseCase.invoke("INPROCESS")
            .onStart { reduce { state.copy(isLoading = true) } }
            .onEach { reduce { state.copy(itemData = it, selected = OrdersType.UPCOMING) } }
            .onCompletion { reduce { state.copy(isLoading = false) } }
            .launchIn(viewModelScope)
    }

    private fun postSideEffect(message: String) {
        intent {
            postSideEffect(OrdersPageContract.SideEffect(message))
        }
    }

    override val container =
        container<OrdersPageContract.UIState, OrdersPageContract.SideEffect>(OrdersPageContract.UIState())
}
