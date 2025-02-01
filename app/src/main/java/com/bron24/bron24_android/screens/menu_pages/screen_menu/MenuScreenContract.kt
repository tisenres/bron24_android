package com.bron24.bron24_android.screens.menu_pages.screen_menu

import kotlinx.coroutines.Job
import org.orbitmvi.orbit.ContainerHost

interface MenuScreenContract {
    interface ViewModel: ContainerHost<UIState, SideEffect> {
        fun onDispatchers(intent: Intent): Job
    }

    class UIState(
        val isLoading:Boolean = true
    )

    class SideEffect(val message:String)

    sealed interface Intent{
        data object MoveToPhone:Intent
    }

    interface Direction{
        suspend fun moveToPhone()
    }
}