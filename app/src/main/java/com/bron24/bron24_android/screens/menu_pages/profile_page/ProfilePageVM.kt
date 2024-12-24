package com.bron24.bron24_android.screens.menu_pages.profile_page

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bron24.bron24_android.domain.usecases.user.GetPersonalUserDataUseCase
import com.bron24.bron24_android.screens.menu_pages.profile_page.ProfilePageContract.Intent.OpenAboutUs
import com.bron24.bron24_android.screens.menu_pages.profile_page.ProfilePageContract.Intent.OpenAddVenue
import com.bron24.bron24_android.screens.menu_pages.profile_page.ProfilePageContract.Intent.OpenChangeLanguage
import com.bron24.bron24_android.screens.menu_pages.profile_page.ProfilePageContract.Intent.OpenEdit
import com.bron24.bron24_android.screens.menu_pages.profile_page.ProfilePageContract.Intent.OpenFavorites
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class ProfilePageVM @Inject constructor(
    private val direction: ProfilePageContract.Direction, private val getPersonalUserDataUseCase: GetPersonalUserDataUseCase
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
        getPersonalUserDataUseCase.execute().onEach {
            it.onSuccess {
                reduce { state.copy(isLoading = false, initial = false, user = it) }
            }.onFailure {
                postSideEffect(sideEffect = ProfilePageContract.SideEffect(message = it.message.toString()))
            }
        }.launchIn(viewModelScope)
    }

    override val container = container<ProfilePageContract.UISate, ProfilePageContract.SideEffect>(ProfilePageContract.UISate())
}