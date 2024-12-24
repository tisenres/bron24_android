package com.bron24.bron24_android.screens.settings.edit_profile

import com.bron24.bron24_android.domain.entity.user.Language
import com.bron24.bron24_android.domain.entity.user.User
import org.orbitmvi.orbit.ContainerHost

interface EditProfileContract {
    interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onDispatchers(intent: Intent)
    }

    data class UIState(
        val user: User = User("","","")
    )

    data class SideEffect(
        val message: String
    )

    sealed interface Intent {
        data object ClickBack : Intent
        data class ClickSave(
            val firstName: String,
            val lastName: String,
            val phone: String
        ) : Intent
        data object ClickDeleteAcc:Intent
        data object ClickEditPhone:Intent
    }
}