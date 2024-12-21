package com.bron24.bron24_android.screens.search.filter_screen

import kotlinx.coroutines.Job
import org.orbitmvi.orbit.ContainerHost

interface FilterScreenContract {
    interface ViewModel: ContainerHost<UIState, SideEffect> {
        fun onDispatchers(intent: Intent): Job
    }

    class UIState(
        val isLoading:Boolean = true
    )

    class SideEffect(val message:String)

    sealed interface Intent
}