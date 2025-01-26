package com.bron24.bron24_android.screens.orderdetails

import com.bron24.bron24_android.domain.entity.order.OrderDetails
import kotlinx.coroutines.Job
import org.orbitmvi.orbit.ContainerHost

interface OrderDetailsContact {
    interface ViewModel: ContainerHost<UIState, SideEffect> {
        fun onDispatchers(intent: Intent): Job
        fun initData(id:Int):Job
    }

    data class UIState(
        val isLoading:Boolean = true,
        val isCanceled:Boolean = false,
        val isCancelling: Boolean = false,
        val order: OrderDetails? = null,
        val imageUrls:List<String> = emptyList()
    )

    class SideEffect(val message:String)

    sealed interface Intent{
        data object ClickMoveTo:Intent
        data object Back :Intent
        data class ClickCancel(val id: Int):Intent
    }

    interface Direction{
        suspend fun moveToNext(id:Int)
        suspend fun back()
    }
}