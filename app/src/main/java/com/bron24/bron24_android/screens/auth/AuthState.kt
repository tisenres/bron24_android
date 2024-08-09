package com.bron24.bron24_android.screens.auth

import com.bron24.bron24_android.domain.entity.auth.AuthResponse
import com.bron24.bron24_android.domain.entity.auth.enums.OTPStatusCode
import com.bron24.bron24_android.domain.entity.auth.enums.PhoneNumberResponseStatusCode

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class OTPRequested(val status: PhoneNumberResponseStatusCode) : AuthState()
    data class OTPVerified(val status: OTPStatusCode) : AuthState()
    data class Authenticated(val response: AuthResponse) : AuthState()
    data class Error(val message: String) : AuthState()
}
