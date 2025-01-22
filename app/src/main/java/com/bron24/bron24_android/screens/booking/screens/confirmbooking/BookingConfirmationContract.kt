package com.bron24.bron24_android.screens.booking.screens.confirmbooking

import com.bron24.bron24_android.common.VenueOrderInfo
import com.bron24.bron24_android.domain.entity.booking.Booking
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
        val fullName:String = "",
        val phoneNumber:String = "",
        val secondPhoneNumber: String? = null,
        val isCheckPhoneNumber:Boolean = false,
        val venueOrderInfo: VenueOrderInfo?= null
    )

    class SideEffect(val message:String)

    sealed interface Intent{
        data object Back:Intent
        data object Confirm:Intent
        data class UpdatePhone(val phone:String):Intent
    }

    interface Direction{
        suspend fun back()
        suspend fun moveToNext(venueOrderInfo: VenueOrderInfo)
    }
}