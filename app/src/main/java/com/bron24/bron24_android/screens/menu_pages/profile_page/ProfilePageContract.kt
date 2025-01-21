package com.bron24.bron24_android.screens.menu_pages.profile_page

import com.bron24.bron24_android.domain.entity.user.User
import kotlinx.coroutines.Job
import org.orbitmvi.orbit.ContainerHost

interface ProfilePageContract {
    interface ViewModel : ContainerHost<UISate, SideEffect> {
        fun onDispatchers(intent: Intent): Job
        fun initData(): Job
    }

    data class UISate(
        val isLoading: Boolean = true,
        val initial: Boolean = false,
        val user: User? = null
    )

    class SideEffect(val message: String)

    interface Direction {
        suspend fun openEdit()
        suspend fun openChangeLanguage()
        suspend fun openFavorites()
        suspend fun openAboutUs()
        suspend fun openAddVenue()
        suspend fun moveToPhoneScreen()
    }

    interface Intent {
        data object OpenEdit : Intent
        data object OpenChangeLanguage : Intent
        data object OpenFavorites : Intent
        data object OpenAboutUs : Intent
        data object OpenAddVenue : Intent
        data object ClickLogout:Intent
    }
}