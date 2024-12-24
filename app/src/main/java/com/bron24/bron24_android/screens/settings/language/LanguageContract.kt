package com.bron24.bron24_android.screens.settings.language

import com.bron24.bron24_android.domain.entity.user.Language
import kotlinx.coroutines.Job
import org.orbitmvi.orbit.ContainerHost

interface LanguageContract {
    interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onDispatchers(intent: Intent): Job
    }

    data class UIState(
        val selectedLanguage: Language = Language("", "")
    )

    data class SideEffect(
        val message: String
    )

    interface Direction {
        suspend fun moveBack()
    }

    interface Intent {
        data object MoveBack : Intent
    }
}