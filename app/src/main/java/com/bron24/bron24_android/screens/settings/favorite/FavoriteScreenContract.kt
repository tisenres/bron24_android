package com.bron24.bron24_android.screens.settings.favorite

import kotlinx.coroutines.Job
import org.orbitmvi.orbit.ContainerHost

interface FavoriteScreenContract {
    interface ViewModel : ContainerHost<UiState, SideEffect> {
        fun onDispatcher(intent: Intent): Job
    }

    data class UiState(
        val isLoading: Boolean = false
    )

    sealed interface SideEffect {

    }

    interface Direction {

    }

    interface Intent {

    }
}