package com.bron24.bron24_android.screens.settings.language

import com.bron24.bron24_android.domain.entity.user.Language
import org.orbitmvi.orbit.ContainerHost

interface LanguageContract {
    interface ViewModel:ContainerHost<UIState,SideEffect>{
        fun onDispatchers(intent:Intent)
    }

    data class UIState(
        val selectedLanguage:Language
    )
    data class SideEffect(
        val message:String
    )
    sealed interface Intent{

    }
}