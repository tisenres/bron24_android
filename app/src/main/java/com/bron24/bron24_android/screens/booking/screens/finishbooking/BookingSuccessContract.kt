package com.bron24.bron24_android.screens.booking.screens.finishbooking

import com.bron24.bron24_android.common.VenueOrderInfo
import com.bron24.bron24_android.domain.entity.booking.Booking
import kotlinx.coroutines.Job
import org.orbitmvi.orbit.ContainerHost

interface BookingSuccessContract {
    interface ViewModel: ContainerHost<UIState, SideEffect> {
        fun onDispatchers(intent:Intent): Job
    }

    data class UIState(
        val os:String = ""
    )

    class SideEffect(val message:String)

    sealed interface Intent{
        data object ClickMenu:Intent
        data object ClickOrder:Intent
    }

    interface Direction{
        suspend fun moveToOrder()
        suspend fun moveToMenu()
    }
}