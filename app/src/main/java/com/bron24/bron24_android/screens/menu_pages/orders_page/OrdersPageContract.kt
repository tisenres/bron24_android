package com.bron24.bron24_android.screens.menu_pages.orders_page

import com.bron24.bron24_android.components.items.OrdersType
import com.bron24.bron24_android.domain.entity.order.Order
import kotlinx.coroutines.Job
import org.orbitmvi.orbit.ContainerHost

interface OrdersPageContract {
    interface ViewModel:ContainerHost<UIState,SideEffect>{
        fun onDispatchers(intent:Intent):Job
        fun initData():Job
    }

    data class UIState(
        val selected:OrdersType = OrdersType.UPCOMING,
        val updateUiState:Boolean = true,
        val isLoading:Boolean = true,
        val refresh:Boolean = false,
        val itemData:List<Order> = emptyList()
    )

    data class SideEffect(
        val message:String
    )

    sealed interface Intent{
        data object ClickHistory:Intent
        data object ClickUpcoming:Intent
        data class ClickItemOrder(val orderId:Int):Intent
        data class Refresh(val type:OrdersType) :Intent
    }

    interface Direction{
        suspend fun moveToInfo(orderId: Long)
    }
}