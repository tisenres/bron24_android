package com.bron24.bron24_android.screens.auth.sms_otp

import kotlinx.coroutines.Job
import org.orbitmvi.orbit.ContainerHost

interface OTPInputContract {
    interface ViewModel: ContainerHost<UIState, SideEffect> {
        fun onDispatchers(intent: Intent): Job
    }

    data class UIState(
        val refreshTime:Int = 90,
        val otpCode: String = "",
        val isLoading:Boolean = false
    )

    class SideEffect(val message:String)

    interface Direction{
        suspend fun moveToMenu()
        suspend fun moveToRegister(phoneNumber: String)
        suspend fun back()
    }

    sealed interface Intent{
        data object ClickBack:Intent
        data class ClickRestart(
            val phoneNumber: String
        ):Intent
        data class RequestOTP(
            val phoneNumber:String,
            val otpCode:Int
        ):Intent
    }

}