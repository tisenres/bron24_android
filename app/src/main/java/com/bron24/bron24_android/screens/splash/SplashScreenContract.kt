package com.bron24.bron24_android.screens.splash

import com.bron24.bron24_android.common.ScreenState
import kotlinx.coroutines.Job
import org.orbitmvi.orbit.ContainerHost

interface SplashScreenContract {
    interface ViewModel: ContainerHost<UIState, SideEffect> {
    }

    class UIState(
        val isLoading:Boolean = true
    )

    class SideEffect(val message:String)

    sealed interface Intent{
        data object Init:Intent
    }

    interface Direction{
        suspend fun moveToNext(state:ScreenState)
    }
}

