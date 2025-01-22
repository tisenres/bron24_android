package com.bron24.bron24_android.screens.on_board

import kotlinx.coroutines.Job
import org.orbitmvi.orbit.ContainerHost

interface OnBoardPagerContract {
    interface ViewModel: ContainerHost<UIState, SideEffect> {
        fun onDispatchers(intent: Intent): Job
    }

    class UIState(
        val isLoading:Boolean = true
    )

    class SideEffect(val message:String)

    sealed interface Intent{
        data object ClickMoveTo:Intent
    }

    interface Direction{
        suspend fun moveToNext()
    }
}