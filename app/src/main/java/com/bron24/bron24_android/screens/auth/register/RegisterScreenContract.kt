package com.bron24.bron24_android.screens.auth.register

import kotlinx.coroutines.Job
import org.orbitmvi.orbit.ContainerHost

interface RegisterScreenContract {
    interface ViewModel: ContainerHost<UIState, SideEffect> {
        fun onDispatchers(intent: Intent): Job
    }

    data class UIState(
        val isLoading:Boolean = false
    )

    class SideEffect(val message:String)

    interface Direction{
        suspend fun back()
        suspend fun moveToNext()
    }

    sealed interface Intent{
        data class ClickRegister(val phone:String,val firstName:String,val lastName:String):Intent
        data object Back:Intent
    }
}