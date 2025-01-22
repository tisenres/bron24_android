package com.bron24.bron24_android.screens.settings.edit_profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bron24.bron24_android.data.local.preference.LocalStorage
import com.bron24.bron24_android.domain.entity.user.UpdateProfile
import com.bron24.bron24_android.domain.usecases.update_profile.DeleteAccountUseCase
import com.bron24.bron24_android.domain.usecases.update_profile.UpdateProfileUseCase
import com.bron24.bron24_android.screens.menu_pages.home_page.HomePageContract
import com.bron24.bron24_android.screens.menu_pages.profile_page.ProfilePageContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject
@HiltViewModel
class EditProfileVM @Inject constructor(
    private val localStorage: LocalStorage,
    private val direction: EditProfileContract.Direction,
    private val deleteAccountUseCase: DeleteAccountUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase
) : ViewModel(), EditProfileContract.ViewModel {
    override fun onDispatchers(intent: EditProfileContract.Intent): Job = intent {
        when(intent){
            EditProfileContract.Intent.ClickBack -> {
                direction.back()
            }
            EditProfileContract.Intent.ClickDeleteAcc -> {
                deleteAccountUseCase.invoke().onStart {
                    reduce { state.copy(isLoading = true) }
                }.onEach {
                    postSideEffect(it)
                    direction.moveToPhoneScreen()
                }.onCompletion {
                    reduce { state.copy(isLoading = false) }
                }.launchIn(viewModelScope)
            }
            EditProfileContract.Intent.ClickEditPhone -> {

            }
            is EditProfileContract.Intent.ClickSave -> {
                if(intent.lastName!=localStorage.lastName||intent.firstName!=localStorage.firstName){
                    updateProfileUseCase.invoke(UpdateProfile(intent.firstName,intent.lastName))
                        .onStart { reduce { state.copy(isLoading = true) } }
                        .onEach {
                        it.onSuccess {
                            direction.back()
                        }.onFailure {
                            postSideEffect(it.message?:"Unknown error!")
                        }
                    }.onCompletion { reduce { state.copy(isLoading = false) } }.launchIn(viewModelScope)
                }else direction.back()

            }
        }
    }
    private fun postSideEffect(message: String) {
        intent {
            postSideEffect(EditProfileContract.SideEffect(message))
        }
    }


    override val container = container<EditProfileContract.UIState, EditProfileContract.SideEffect>(
        EditProfileContract.UIState(
            phoneNumber = localStorage.phoneNumber,
            firstName = localStorage.firstName,
            lastName = localStorage.lastName
        )
    )
}