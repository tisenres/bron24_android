package com.bron24.bron24_android.screens.language

import com.bron24.bron24_android.domain.entity.user.Language
import kotlinx.coroutines.Job
import org.orbitmvi.orbit.ContainerHost

interface LanguageSelContract {
    interface ViewModel:ContainerHost<UIState,SideEffect>{
        fun onDispatchers(intent: Intent): Job
        fun initData():Job
    }

    data class UIState(
        val selectedLanguage:Language = Language("uz","O`zbek") ,
        val languages : List<Language> = emptyList(),
        val reComposition:Boolean= false
    )

    class SideEffect(val message:String)

    interface Direction{
        suspend fun moveToNext()
    }

    sealed interface Intent{
        data class SelectedLanguage(val language:Language):Intent
        data object ClickMoveTo:Intent
    }
}