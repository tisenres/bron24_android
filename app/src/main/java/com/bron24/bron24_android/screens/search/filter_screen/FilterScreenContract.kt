package com.bron24.bron24_android.screens.search.filter_screen

import com.bron24.bron24_android.common.FilterOptions
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

    sealed interface Intent{
        data object ClickBack:Intent
        data class ClickFilterBtn(val filterOptions:FilterOptions):Intent
    }

    interface Direction{
        suspend fun back()
    }
}