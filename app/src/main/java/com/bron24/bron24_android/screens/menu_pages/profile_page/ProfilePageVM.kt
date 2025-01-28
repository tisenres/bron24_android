package com.bron24.bron24_android.screens.menu_pages.profile_page

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bron24.bron24_android.data.local.preference.LocalStorage
import com.bron24.bron24_android.domain.entity.user.User
import com.bron24.bron24_android.domain.usecases.update_profile.LogoutUseCase
import com.bron24.bron24_android.helper.util.formatPhoneNumber
import com.bron24.bron24_android.screens.menu_pages.profile_page.ProfilePageContract.Intent.OpenAboutUs
import com.bron24.bron24_android.screens.menu_pages.profile_page.ProfilePageContract.Intent.OpenAddVenue
import com.bron24.bron24_android.screens.menu_pages.profile_page.ProfilePageContract.Intent.OpenChangeLanguage
import com.bron24.bron24_android.screens.menu_pages.profile_page.ProfilePageContract.Intent.OpenEdit
import com.bron24.bron24_android.screens.menu_pages.profile_page.ProfilePageContract.Intent.OpenFavorites
import com.bron24.bron24_android.screens.menu_pages.profile_page.ProfilePageContract.Intent.ClickLogout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class ProfilePageVM @Inject constructor(
    private val direction: ProfilePageContract.Direction,
    private val localStorage: LocalStorage,
    private val logoutUseCase: LogoutUseCase
) : ViewModel(), ProfilePageContract.ViewModel {

    override fun onDispatchers(intent: ProfilePageContract.Intent) = intent {
        when (intent) {
            OpenEdit -> {
                direction.openEdit()
            }

            OpenChangeLanguage -> {
                direction.openChangeLanguage()
            }

            OpenFavorites -> {
                direction.openFavorites()
            }

            OpenAboutUs -> {
                direction.openAboutUs()
            }

            OpenAddVenue -> {
                direction.openAddVenue()
            }

            ClickLogout -> {
                logoutUseCase.invoke().onStart {
                    reduce { state.copy(isLoading = true) }
                }.onEach {
                    it.onSuccess {
                        direction.moveToPhoneScreen()
                    }.onFailure {
                        postSideEffect(it.message ?: "Unknown error!")
                    }
                }.onCompletion {
                    reduce { state.copy(isLoading = false) }
                }.launchIn(viewModelScope)
            }
        }
    }

    private fun postSideEffect(message: String) {
        intent {
            postSideEffect(ProfilePageContract.SideEffect(message))
        }
    }

    override val container =
        container<ProfilePageContract.UIState, ProfilePageContract.SideEffect>(ProfilePageContract.UIState(isLoading = false, user = User(localStorage.firstName,localStorage.lastName,localStorage.phoneNumber.formatPhoneNumber())))
}