package com.bron24.bron24_android.screens.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bron24.bron24_android.domain.entity.user.User
import com.bron24.bron24_android.domain.usecases.auth.AuthenticateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class RegisterScreenVM @Inject constructor(
    private val authenticateUC: AuthenticateUserUseCase,
    private val direction: RegisterScreenContract.Direction
) : ViewModel(), RegisterScreenContract.ViewModel {
    override fun onDispatchers(intent: RegisterScreenContract.Intent) = intent {
        when (intent) {
            RegisterScreenContract.Intent.Back -> direction.back()
            is RegisterScreenContract.Intent.ClickRegister -> {
                authenticateUC.invoke(
                    user = User(
                        firstName = intent.firstName,
                        lastName = intent.lastName,
                        phoneNumber = intent.phone
                    ),
                    userExists = false
                ).onStart { reduce { state.copy(isLoading = true) }
                }.onEach {
                     it.onSuccess {
                         direction.moveToNext()
                     }.onFailure {
                         postSideEffect(it.message.toString())
                     }
                }.launchIn(viewModelScope)
            }
        }
    }
    private fun postSideEffect(message: String) {
        intent {
            postSideEffect(RegisterScreenContract.SideEffect(message))
        }
    }

    override val container =
        container<RegisterScreenContract.UIState, RegisterScreenContract.SideEffect>(
            RegisterScreenContract.UIState()
        )
}