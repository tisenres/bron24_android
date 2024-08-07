package com.bron24.bron24_android.screens.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val model: AuthModel
) : ViewModel() {

    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber: StateFlow<String> get() = _phoneNumber

    private val _otp = MutableStateFlow(0)
    val otp: StateFlow<Int> get() = _otp

    private val _token = MutableStateFlow("")
    val token: StateFlow<String> get() = _token

    private val _otpRequestStatus = MutableStateFlow(false)
    val otpRequestStatus: StateFlow<Boolean> get() = _otpRequestStatus

    private val _otpVerifyStatus = MutableStateFlow(false)
    val otpVerifyStatus: StateFlow<Boolean> get() = _otpVerifyStatus

    private val _isTokenExpired = MutableStateFlow(false)
    val isTokenExpired: StateFlow<Boolean> get() = _isTokenExpired

    private val _isPhoneNumberValid = MutableStateFlow(false)
    val isPhoneNumberValid: StateFlow<Boolean> = _isPhoneNumberValid

    fun updateOTP(code: Int) {
        _otp.value = code
    }

    fun updatePhoneNumber(phone: String) {
        _phoneNumber.value = phone
        _isPhoneNumberValid.value = isValidUzbekPhoneNumber(phone)
    }

    private fun isValidUzbekPhoneNumber(phone: String): Boolean {
        val regex = "^\\+998[0-9]{9}$".toRegex()
        return regex.matches(phone)
    }

    fun requestOTP() {
        viewModelScope.launch {
            val response = model.requestOTP(
                _phoneNumber.value.replace("+", "")
            )
            _otpRequestStatus.value = response.success == true
        }
    }

    fun verifyOTP() {
        viewModelScope.launch {
            val response = model.verifyOTP(
                _phoneNumber.value.replace("+", ""),
                _otp.value
            )
            if (response.success == true) {
                _token.value = response.access ?: ""
                _otpVerifyStatus.value = true
                _isTokenExpired.value = false
            } else {
                _otpVerifyStatus.value = false
            }
        }
    }

    fun checkTokenValidity() {
        _isTokenExpired.value = model.isTokenExpired()
    }

    fun logout() {
        viewModelScope.launch {
            model.clearToken()
        }
    }
}