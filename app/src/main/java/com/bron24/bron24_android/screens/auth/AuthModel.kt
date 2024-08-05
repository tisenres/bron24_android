package com.bron24.bron24_android.screens.auth

import com.bron24.bron24_android.domain.entity.auth.OTPResponse
import com.bron24.bron24_android.domain.usecases.auth.RequestOTPUseCase
import com.bron24.bron24_android.domain.usecases.auth.VerifyOTPUseCase
import javax.inject.Inject

class AuthModel @Inject constructor(
    private val requestOTPUseCase: RequestOTPUseCase,
    private val verifyOTPUseCase: VerifyOTPUseCase
) {
    suspend fun requestOTPUseCase(phoneNumber: String): OTPResponse {
        return requestOTPUseCase.execute(phoneNumber)
    }

    suspend fun verifyOTPUseCase(phoneNumber: String, otp: String): OTPResponse {
        return verifyOTPUseCase.execute(phoneNumber, otp)
    }
}