package com.bron24.bron24_android.screens.orderdetails

import kotlinx.coroutines.Job
import org.orbitmvi.orbit.ContainerHost

interface OrderDetailsContact {
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