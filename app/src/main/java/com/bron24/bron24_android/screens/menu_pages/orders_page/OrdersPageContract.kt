package com.bron24.bron24_android.screens.menu_pages.orders_page

import com.bron24.bron24_android.data.network.dto.orders.OrderDto
import kotlinx.coroutines.Job
import org.orbitmvi.orbit.ContainerHost

interface OrdersPageContract {
    interface ViewModel:ContainerHost<UIState,SideEffect>{
        fun onDispatchers(intent:Intent):Job
        fun initData():Job
    }

    data class UIState(
        val isLoading:Boolean = true,
        val itemData:List<OrderDto> = emptyList()
    )

    data class SideEffect(
        val message:String
    )

    sealed interface Intent{
        data object ClickHistory:Intent
        data object ClickUpcoming:Intent
        data class ClickItemOrder(val order:OrderDto):Intent
    }

    interface Direction{
        suspend fun moveToInfo(order: OrderDto)
    }
}