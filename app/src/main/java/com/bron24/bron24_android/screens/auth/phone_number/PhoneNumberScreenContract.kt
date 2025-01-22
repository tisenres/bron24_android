package com.bron24.bron24_android.screens.auth.phone_number

import kotlinx.coroutines.Job
import org.orbitmvi.orbit.ContainerHost

interface PhoneNumberScreenContract {
    interface ViewModel: ContainerHost<UIState, SideEffect> {
        fun onDispatchers(intent: Intent): Job
    }

    data class UIState(
        val isValidPhoneNumber:Boolean = false,
    )

    data class SideEffect(val message:String)

    interface Direction{
        suspend fun moveToNext(phoneNumber: String)
    }

    sealed interface Intent{
        data class ClickNextBtn(val phoneNumber:String):Intent
        data class UpdatePhone(val phoneNumber: String):Intent
    }
}