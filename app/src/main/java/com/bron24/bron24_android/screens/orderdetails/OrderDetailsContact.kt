package com.bron24.bron24_android.screens.orderdetails

import com.bron24.bron24_android.common.VenueOrderInfo
import com.bron24.bron24_android.domain.entity.order.OrderDetails
import com.bron24.bron24_android.domain.entity.venue.VenueDetails
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
        val imageUrls:List<String> = emptyList(),
        val venueDetails: VenueDetails? = null,
    )

    class SideEffect(val message:String)

    sealed interface Intent{
        data class OpenVenueDetails(val id:Int):Intent
        data object Back :Intent
        data class ClickCancel(val id: Int):Intent
        data class ClickOrder(val info:VenueOrderInfo):Intent
    }

    interface Direction{
        suspend fun moveToNext(info: VenueOrderInfo)
        suspend fun back()
    }
}