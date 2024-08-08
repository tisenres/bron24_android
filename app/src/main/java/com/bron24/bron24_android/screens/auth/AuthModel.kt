package com.bron24.bron24_android.screens.auth

import com.bron24.bron24_android.domain.entity.auth.OTPCodeResponse
import com.bron24.bron24_android.domain.entity.auth.PhoneNumberResponse
import com.bron24.bron24_android.domain.usecases.auth.*
import javax.inject.Inject

class AuthModel @Inject constructor(
    private val requestOTPUseCase: RequestOTPUseCase,
) {
    suspend fun requestOTP(phoneNumber: String): PhoneNumberResponse {
        return requestOTPUseCase.execute(phoneNumber)
    }

    suspend fun verifyOTP(phoneNumber: String, otp: Int): OTPCodeResponse {

    }

    fun isTokenExpired(): Boolean {

    }

    fun clearToken() {

    }
}