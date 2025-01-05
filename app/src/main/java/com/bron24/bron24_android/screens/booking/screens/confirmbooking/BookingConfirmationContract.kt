package com.bron24.bron24_android.screens.booking.screens.confirmbooking

import com.bron24.bron24_android.common.ScreenState
import com.bron24.bron24_android.common.VenueOrderInfo
import com.bron24.bron24_android.domain.entity.booking.Booking
import com.bron24.bron24_android.screens.auth.phone_number.PhoneNumberScreen
import kotlinx.coroutines.Job
import org.orbitmvi.orbit.ContainerHost

interface BookingConfirmationContract {
    interface ViewModel: ContainerHost<UIState, SideEffect> {
        fun onDispatchers(intent:Intent):Job
        fun initData(venueOrderInfo: VenueOrderInfo):Job
    }

    data class UIState(
        val isLoading:Boolean = true,
        val booking:Booking? = null,
        val secondPhoneNumber: String = "",
        val isCheckPhoneNumber:Boolean = false
    )

    class SideEffect(val message:String)

    sealed interface Intent{
        data object Back:Intent
        data object Confirm:Intent
        data class UpdatePhone(val phone:String):Intent
    }

    interface Direction{
        suspend fun moveToNext(state: ScreenState)
    }
}