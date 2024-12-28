package com.bron24.bron24_android.screens.menu_pages.profile_page

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bron24.bron24_android.data.local.preference.LocalStorage
import com.bron24.bron24_android.domain.entity.user.User
import com.bron24.bron24_android.helper.util.formatPhoneNumber
import com.bron24.bron24_android.screens.menu_pages.profile_page.ProfilePageContract.Intent.OpenAboutUs
import com.bron24.bron24_android.screens.menu_pages.profile_page.ProfilePageContract.Intent.OpenAddVenue
import com.bron24.bron24_android.screens.menu_pages.profile_page.ProfilePageContract.Intent.OpenChangeLanguage
import com.bron24.bron24_android.screens.menu_pages.profile_page.ProfilePageContract.Intent.OpenEdit
import com.bron24.bron24_android.screens.menu_pages.profile_page.ProfilePageContract.Intent.OpenFavorites
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class ProfilePageVM @Inject constructor(
    private val direction: ProfilePageContract.Direction,
    private val localStorage: LocalStorage
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
        }
    }

    override fun initData() = intent {
        reduce {
            state.copy(
                isLoading = false,
                user = User(
                    localStorage.firstName,
                    localStorage.lastName,
                    localStorage.phoneNumber.formatPhoneNumber()
                )
            )
        }
    }

    override val container =
        container<ProfilePageContract.UISate, ProfilePageContract.SideEffect>(ProfilePageContract.UISate())
}