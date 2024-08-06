package com.bron24.bron24_android.screens.auth

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MockAuthViewModel : ViewModel() {
    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber: StateFlow<String> get() = _phoneNumber

    private val _otp = MutableStateFlow("")
    val otp: StateFlow<String> get() = _otp

    private val _token = MutableStateFlow("")
    val token: StateFlow<String> get() = _token

    private val _otpRequestStatus = MutableStateFlow(false)
    val otpRequestStatus: StateFlow<Boolean> get() = _otpRequestStatus

    private val _otpVerifyStatus = MutableStateFlow(false)
    val otpVerifyStatus: StateFlow<Boolean> get() = _otpVerifyStatus

    private val _isPhoneNumberValid = MutableStateFlow(false)
    val isPhoneNumberValid: StateFlow<Boolean> = _isPhoneNumberValid

    fun updatePhoneNumber(phone: String) {
        _phoneNumber.value = phone
    }

    fun updateOTP(code: String) {
        _otp.value = code
    }

    fun requestOTP() {
        _otpRequestStatus.value = true
    }

    fun verifyOTP() {
        _otpVerifyStatus.value = true
    }
}
