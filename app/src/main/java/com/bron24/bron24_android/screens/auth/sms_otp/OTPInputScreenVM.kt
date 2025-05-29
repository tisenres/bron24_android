package com.bron24.bron24_android.screens.auth.sms_otp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bron24.bron24_android.data.local.preference.LocalStorage
import com.bron24.bron24_android.domain.entity.auth.enums.OTPStatusCode
import com.bron24.bron24_android.domain.entity.auth.enums.PhoneNumberResponseStatusCode
import com.bron24.bron24_android.domain.entity.user.User
import com.bron24.bron24_android.domain.usecases.auth.AuthenticateUserUseCase
import com.bron24.bron24_android.domain.usecases.auth.RequestOTPUseCase
import com.bron24.bron24_android.domain.usecases.auth.VerifyOTPUseCase
import com.bron24.bron24_android.domain.usecases.location.CheckLocationPermissionUseCase
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
    private val localStorage: LocalStorage,
    private val checkLocationPermissionUseCase: CheckLocationPermissionUseCase
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
                    reduce { state.copy(isLoading = false, otpCode = "") }
                    it.onSuccess {
                        when(it.result){
                            OTPStatusCode.CORRECT_OTP -> {
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
                                            direction.moveToNext()
                                        }
                                    }.launchIn(viewModelScope)
                                } else {
                                    direction.moveToRegister(intent.phoneNumber.substring(1))
                                }
                            }
                            OTPStatusCode.INCORRECT_OTP -> {
                                reduce { state.copy(otpCode = "") }
                                postSideEffect(code = OTPStatusCode.INCORRECT_OTP)
                            }
                            OTPStatusCode.NETWORK_ERROR -> {
                                postSideEffect(code = OTPStatusCode.NETWORK_ERROR)
                            }
                            OTPStatusCode.UNKNOWN_ERROR -> {
                                postSideEffect(code = OTPStatusCode.UNKNOWN_ERROR)
                            }

                            OTPStatusCode.BANNED_USER -> {
                                postSideEffect(code = OTPStatusCode.BANNED_USER)
                            }
                        }

                    }.onFailure {
                        postSideEffect(message = it.message.toString())
                    }
                }.launchIn(viewModelScope)
            }

            is OTPInputContract.Intent.ClickRestart -> {
                requestOTPUseCase.invoke(intent.phoneNumber).onEach {

                    it.onSuccess {
                        when (it.result) {
                            PhoneNumberResponseStatusCode.SUCCESS -> {

                            }

                            PhoneNumberResponseStatusCode.MANY_REQUESTS -> {
                                postSideEffect(OTPStatusCode.NETWORK_ERROR)
                            }

                            PhoneNumberResponseStatusCode.INCORRECT_PHONE_NUMBER -> {
                                postSideEffect(message = "Incorrect phone number. Please try again!")
                            }

                            PhoneNumberResponseStatusCode.NETWORK_ERROR -> {
                                postSideEffect(OTPStatusCode.NETWORK_ERROR)
                            }

                            PhoneNumberResponseStatusCode.UNKNOWN_ERROR -> {
                                postSideEffect(OTPStatusCode.UNKNOWN_ERROR)
                            }
                        }

                    }.onFailure {
                        postSideEffect(message = it.message.toString())
                    }
                }.launchIn(viewModelScope)
            }

            is OTPInputContract.Intent.OTPCode -> {
                reduce { state.copy(otpCode = intent.otpCode) }
            }
        }
    }

    private fun postSideEffect(code: OTPStatusCode = OTPStatusCode.CORRECT_OTP, message:String ="") {
        intent {
            postSideEffect(OTPInputContract.SideEffect(status = code, message = message))
        }
    }

    override val container =
        container<OTPInputContract.UIState, OTPInputContract.SideEffect>(OTPInputContract.UIState())
}