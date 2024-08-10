package com.bron24.bron24_android.screens.auth

import com.bron24.bron24_android.domain.entity.auth.AuthResponse
import com.bron24.bron24_android.domain.entity.auth.OTPCodeResponse
import com.bron24.bron24_android.domain.entity.auth.PhoneNumberResponse
import com.bron24.bron24_android.domain.entity.user.User
import com.bron24.bron24_android.domain.usecases.auth.*
import javax.inject.Inject

class AuthModel @Inject constructor(
    private val authFlowUseCase: AuthenticateFlowUseCase,
) {
    suspend fun requestOTP(phoneNumber: String): PhoneNumberResponse {
        return authFlowUseCase.requestOTP(phoneNumber)
    }

    suspend fun verifyOTP(phoneNumber: String, otp: Int): OTPCodeResponse {
        return authFlowUseCase.verifyOTP(phoneNumber, otp)
    }

    suspend fun authUser(phoneNumber: String, firstName: String, lastName: String): AuthResponse {
        return authFlowUseCase.authenticateUser(
            User(
                firstName = firstName,
                lastName = lastName,
                phoneNumber = phoneNumber,
            )
        )
    }
}