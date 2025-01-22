package com.bron24.bron24_android.screens.settings.sms_changes

import com.bron24.bron24_android.domain.entity.user.User
import kotlinx.coroutines.Job
import org.orbitmvi.orbit.ContainerHost

interface SimCardContract {
    interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onDispatchers(intent: Intent)
    }

    data class UIState(
        val phoneNumber:String = ""
    )

    data class SideEffect(
        val message: String
    )

    sealed interface Intent {
        data object ClickBack : Intent
        data object ClickNextBtn:Intent
    }
    interface Direction{
        suspend fun back()
        suspend fun moveToNext(phone:String)
    }
}