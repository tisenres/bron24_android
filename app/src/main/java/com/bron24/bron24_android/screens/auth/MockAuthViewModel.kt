package com.bron24.bron24_android.screens.auth

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import com.bron24.bron24_android.domain.entity.auth.AuthResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MockAuthViewModel : ViewModel() {



    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> get() = _authState

    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber: StateFlow<String> get() = _phoneNumber

    private val _isPhoneNumberValid = MutableStateFlow(true)
    val isPhoneNumberValid: StateFlow<Boolean> get() = _isPhoneNumberValid


    fun authenticateUser(firstName: String, lastName: String) {
        _authState.value = AuthState.Loading

        // Simulate network delay and response
        _authState.value = if (firstName == "John" && lastName == "Doe") {
            AuthState.Authenticated(
                response = AuthResponse(
                    accessToken = "mockAccessToken",
                    refreshToken = "mockRefreshToken",
                )
            )
        } else {
            AuthState.Error("Authentication failed. Invalid credentials.")
        }
    }

    fun clearState() {
        _authState.value = AuthState.Idle
    }

    fun updateOTP(toInt: Int) {


    }

    fun verifyOTP() {


    }

    fun updatePhoneNumber(newValue: String) {


    }

    fun requestOTP() {


    }
}
