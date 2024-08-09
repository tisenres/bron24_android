package com.bron24.bron24_android.screens.auth

import com.bron24.bron24_android.domain.entity.auth.AuthResponse

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class OTPRequested(val success: Boolean) : AuthState()
    data class OTPVerified(val success: Boolean) : AuthState()
    data class Authenticated(val response: AuthResponse) : AuthState()
    data class Error(val message: String) : AuthState()
}