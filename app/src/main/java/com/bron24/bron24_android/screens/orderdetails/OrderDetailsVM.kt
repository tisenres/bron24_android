package com.bron24.bron24_android.screens.orderdetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bron24.bron24_android.domain.usecases.orders.CancelOrderUseCase
import com.bron24.bron24_android.domain.usecases.orders.GetOrderDetailsUseCase
import com.bron24.bron24_android.screens.menu_pages.profile_page.ProfilePageContract
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
            is OrderDetailsContact.Intent.ClickMoveTo -> {
                direction.moveToNext(intent.id)
            }

            OrderDetailsContact.Intent.Back -> {
                direction.back()
            }

            is OrderDetailsContact.Intent.ClickCancel -> {
                cancelOrderUseCase.invoke(intent.id).onEach {
                    it.onSuccess {
                        direction.back()
                    }.onFailure {
                        postSideEffect(message = it.message?:"")
                    }
                }.launchIn(viewModelScope)
            }
        }
    }
    private fun postSideEffect(message: String) {
        intent {
            postSideEffect(OrderDetailsContact.SideEffect(message))
        }
    }

    override fun initData(id: Int): Job = intent {
        orderDetailsUseCase.execute(id)
            .onStart {
                reduce { state.copy(isLoading = true) }
            }.onEach {
//                Log.d("AAA", "initData: ${it.second}")
                reduce { state.copy(order = it.first, imageUrls = it.second, isLoading = false) }
                when (it.first.status) {
                    "INPROCESS" -> {
                        reduce { state.copy(isCancelling = true, isCanceled = true) }
                    }

                    else -> {
                        reduce { state.copy(isCancelling = false) }
                    }
                }
            }.launchIn(viewModelScope)
    }

    override val container = container<OrderDetailsContact.UIState, OrderDetailsContact.SideEffect>(OrderDetailsContact.UIState())
}