package com.bron24.bron24_android.screens.auth.sms_otp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bron24.bron24_android.data.local.preference.LocalStorage
import com.bron24.bron24_android.domain.entity.auth.enums.PhoneNumberResponseStatusCode
import com.bron24.bron24_android.domain.entity.user.User
import com.bron24.bron24_android.domain.usecases.auth.AuthenticateUserUseCase
import com.bron24.bron24_android.domain.usecases.auth.RequestOTPUseCase
import com.bron24.bron24_android.domain.usecases.auth.VerifyOTPUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class OTPInputScreenVM @Inject constructor(
    private val requestOTPUseCase: RequestOTPUseCase,
    private val direction: OTPInputContract.Direction,
    private val verifyOTPUseCase: VerifyOTPUseCase,
    private val authenticateUserUseCase: AuthenticateUserUseCase,
    private val localStorage: LocalStorage
) : ViewModel(), OTPInputContract.ViewModel {
    override fun onDispatchers(intent: OTPInputContract.Intent): Job = intent {
        when (intent) {
            OTPInputContract.Intent.ClickBack -> {
                direction.back()
            }
            is OTPInputContract.Intent.RequestOTP -> {
                verifyOTPUseCase.invoke(
                    intent.phoneNumber.substring(1), intent.otpCode
                ).onStart {
                    reduce { state.copy(isLoading = true) }
                }.onEach {
                    reduce { state.copy(isLoading = false) }
                    it.onSuccess {
                        if (it.userExists) {
                            authenticateUserUseCase.invoke(
                                User(
                                    localStorage.firstName,
                                    localStorage.lastName,
                                    intent.phoneNumber.substring(1)
                                ),
                                userExists = true
                            ).onEach {
                                it.onSuccess {
                                    direction.moveToMenu()
                                }
                            }.launchIn(viewModelScope)
                        } else {
                            direction.moveToRegister(intent.phoneNumber.substring(1))
                        }
                    }.onFailure {
                        postSideEffect(it.message.toString())
                    }
                }.launchIn(viewModelScope)
            }

            is OTPInputContract.Intent.ClickRestart -> {
                requestOTPUseCase.invoke(intent.phoneNumber).onEach {
                    it.onSuccess {
                        when (it.result) {
                            PhoneNumberResponseStatusCode.SUCCESS -> {
                                reduce { state.copy(refreshTime = 90, otpCode = "") }
                            }

                            PhoneNumberResponseStatusCode.MANY_REQUESTS -> {
                                postSideEffect("Bog'lanishda muammo yuzaga keldi iltimos keyinroq urinib ko'ring!")
                            }

                            PhoneNumberResponseStatusCode.INCORRECT_PHONE_NUMBER -> {
                                postSideEffect(message = "Incorrect phone number. Please try again!")
                            }

                            PhoneNumberResponseStatusCode.NETWORK_ERROR -> {
                                postSideEffect("Internetni tekshiring!")
                            }

                            PhoneNumberResponseStatusCode.UNKNOWN_ERROR -> {
                                postSideEffect("Unknown error!")
                            }
                        }

                    }.onFailure {
                        postSideEffect(it.message.toString())
                    }
                }.launchIn(viewModelScope)
            }
        }
    }

    private fun postSideEffect(message: String) {
        intent {
            postSideEffect(OTPInputContract.SideEffect(message))
        }
    }

    override val container =
        container<OTPInputContract.UISate, OTPInputContract.SideEffect>(OTPInputContract.UISate())
}