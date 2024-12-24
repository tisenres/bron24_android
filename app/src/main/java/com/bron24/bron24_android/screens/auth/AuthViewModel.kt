package com.bron24.bron24_android.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bron24.bron24_android.helper.extension.isValidUzbekPhoneNumber
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val model: AuthModel
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> get() = _authState

    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber: StateFlow<String> get() = _phoneNumber

    private val _otp = MutableStateFlow(0)
    val otp: StateFlow<Int> get() = _otp

    private val _isPhoneNumberValid = MutableStateFlow(false)
    val isPhoneNumberValid: StateFlow<Boolean> = _isPhoneNumberValid

    fun updateOTP(code: Int) {
        _otp.value = code
    }

    fun updatePhoneNumber(phone: String) {
        _phoneNumber.value = phone
        _isPhoneNumberValid.value = phone.isValidUzbekPhoneNumber()
    }



//    fun requestOTP() {
//        viewModelScope.launch {
//            _authState.value = AuthState.Loading
//            try {
//                val response = model.requestOTP(
//                    _phoneNumber.value.replace("+", "")
//                )
//                _authState.value = AuthState.OTPRequested(response.result)
//            } catch (e: Exception) {
//                _authState.value = AuthState.Error(e.message ?: "Unknown error occurred")
//            }
//        }
//    }

//    fun verifyOTP() {
//        viewModelScope.launch {
//            _authState.value = AuthState.Loading
//            try {
//                val response = model.verifyOTP(
//                    _phoneNumber.value.replace("+", ""),
//                    _otp.value
//                )
//                _authState.value = AuthState.OTPVerified(response.result, response.userExists)
//            } catch (e: Exception) {
//                _authState.value = AuthState.Error(e.message ?: "Unknown error occurred")
//            }
//        }
//    }

//    fun authenticateUser(firstName: String, lastName: String) {
//        viewModelScope.launch {
//            _authState.value = AuthState.Loading
//            try {
//                val response = model.authUser(
//                    _phoneNumber.value.replace("+", ""),
//                    firstName,
//                    lastName
//                )
//                _authState.value = AuthState.Authenticated(response)
//            } catch (e: Exception) {
//                _authState.value = AuthState.Error(e.message ?: "Authentication failed")
//            }
//        }
//    }

    fun clearState() {
        _authState.value = AuthState.Idle
        _otp.value = 0
    }
}