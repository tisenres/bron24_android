package com.bron24.bron24_android.screens.auth.phone_number

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bron24.bron24_android.data.local.preference.LocalStorage
import com.bron24.bron24_android.domain.entity.auth.enums.PhoneNumberResponseStatusCode
import com.bron24.bron24_android.domain.usecases.auth.RequestOTPUseCase
import com.bron24.bron24_android.helper.util.isValidUzbekPhoneNumber
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class PhoneNumberScreenVM @Inject constructor(
    private val requestOTPUseCase: RequestOTPUseCase,
    private val direction: PhoneNumberScreenContract.Direction,
    private val localStorage: LocalStorage
) : ViewModel(), PhoneNumberScreenContract.ViewModel {
    init {
        localStorage.openPhoneNumber = true
    }
    override fun onDispatchers(intent: PhoneNumberScreenContract.Intent): Job = intent {
        when (intent) {
            is PhoneNumberScreenContract.Intent.ClickNextBtn -> {
                if (intent.phoneNumber.isValidUzbekPhoneNumber()) {
                    requestOTPUseCase.invoke(intent.phoneNumber).onEach {
                        direction.moveToNext(phoneNumber = intent.phoneNumber)
                        it.onSuccess { status ->
                            direction.moveToNext(phoneNumber = intent.phoneNumber)
                            when (status.result) {
                                PhoneNumberResponseStatusCode.SUCCESS -> {
                                    reduce { state.copy(isValidPhoneNumber = false) }
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
                            postSideEffect(message = it.message.toString())
                        }
                    }.launchIn(viewModelScope)
                } else {
                    reduce { state.copy(isValidPhoneNumber = false) }
                }
            }

            is PhoneNumberScreenContract.Intent.UpdatePhone -> {
                if(intent.phoneNumber.isValidUzbekPhoneNumber()){
                    reduce { state.copy(isValidPhoneNumber = true) }
                }else{
                    reduce { state.copy(isValidPhoneNumber = false) }
                }
            }
        }

    }

    private fun postSideEffect(message: String) {
        intent {
            postSideEffect(PhoneNumberScreenContract.SideEffect(message))
        }
    }

    override val container =
        container<PhoneNumberScreenContract.UIState, PhoneNumberScreenContract.SideEffect>(
            PhoneNumberScreenContract.UIState()
        )
}