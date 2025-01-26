package com.bron24.bron24_android.screens.orderdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bron24.bron24_android.domain.usecases.orders.CancelOrderUseCase
import com.bron24.bron24_android.domain.usecases.orders.GetOrderDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class OrderDetailsVM @Inject constructor(
    private val orderDetailsUseCase: GetOrderDetailsUseCase,
    private val cancelOrderUseCase: CancelOrderUseCase,
    private val direction: OrderDetailsContact.Direction
) : ViewModel(), OrderDetailsContact.ViewModel {
    override fun onDispatchers(intent: OrderDetailsContact.Intent): Job = intent {
        when (intent) {
            OrderDetailsContact.Intent.ClickMoveTo -> {

            }

            OrderDetailsContact.Intent.Back -> {
                direction.back()
            }

            is OrderDetailsContact.Intent.ClickCancel -> {
                cancelOrderUseCase.invoke(intent.id)
                    .onStart {
                    }.onEach {
                       direction.back()
                    }.launchIn(viewModelScope)
            }
        }
    }

    override fun initData(id: Int): Job = intent {
        orderDetailsUseCase.execute(id)
            .onStart {
                reduce { state.copy(isLoading = true) }
            }.onEach {
                reduce { state.copy(order = it.first, imageUrls = it.second) }
                when (it.first.status) {
                    "INPROCESS" -> {
                        reduce { state.copy(isCancelling = true, isCanceled = true) }
                    }

                    else -> {
                        reduce { state.copy(isCancelling = false) }
                    }
                }
            }.onCompletion {
                reduce { state.copy(isLoading = false) }
            }.launchIn(viewModelScope)
    }

    override val container = container<OrderDetailsContact.UIState, OrderDetailsContact.SideEffect>(OrderDetailsContact.UIState())
}