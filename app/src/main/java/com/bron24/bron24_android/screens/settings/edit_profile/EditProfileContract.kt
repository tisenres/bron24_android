package com.bron24.bron24_android.screens.settings.edit_profile

import com.bron24.bron24_android.domain.entity.user.Language
import com.bron24.bron24_android.domain.entity.user.User
import kotlinx.coroutines.Job
import org.orbitmvi.orbit.ContainerHost

interface EditProfileContract {
    interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onDispatchers(intent: Intent):Job
    }

    data class UIState(
        val isLoading:Boolean = false,
        val phoneNumber:String,
        val firstName: String,
        val lastName:String,
        val message: String = "",
    )

    data class SideEffect(
        val message: String
    )

    sealed interface Intent {
        data object ClickBack : Intent
        data class ClickSave(
            val firstName: String,
            val lastName: String,
        ) : Intent
        data object ClickDeleteAcc:Intent
        data object ClickEditPhone:Intent
    }

    interface Direction{
        suspend fun back()
        suspend fun moveToPhoneScreen()
    }
}