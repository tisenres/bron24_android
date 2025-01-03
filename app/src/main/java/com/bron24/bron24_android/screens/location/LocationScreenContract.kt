package com.bron24.bron24_android.screens.location

import com.bron24.bron24_android.common.ScreenState
import com.bron24.bron24_android.domain.entity.user.LocationPermissionState
import kotlinx.coroutines.Job
import org.orbitmvi.orbit.ContainerHost

interface LocationScreenContract {
    interface ViewModel: ContainerHost<UIState, SideEffect> {
        fun onDispatchers(intent:Intent):Job
    }

    data class UIState(
        val checkLocation:Boolean = false
    )

    class SideEffect(val message:String)

    sealed interface Intent{
        data object ClickAllow:Intent
        data object ClickDeny:Intent
    }

    interface Direction{
        suspend fun moveToNext()
    }
}