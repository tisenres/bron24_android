package com.bron24.bron24_android.screens.settings.success

import kotlinx.coroutines.Job
import org.orbitmvi.orbit.ContainerHost

interface SuccessScreenContract {
    interface ViewModel : ContainerHost<UiState, SideEffect> {
        fun onDispatchers(intent: Intent): Job
    }

    data class UiState(val isLoading: Boolean = false)

    sealed interface SideEffect {

    }

    interface Direction {

    }

    interface Intent {

    }
}