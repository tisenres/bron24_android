package com.bron24.bron24_android.domain.usecases.auth

import com.bron24.bron24_android.domain.entity.auth.OTPRequest
import com.bron24.bron24_android.domain.entity.auth.OTPResponse
import com.bron24.bron24_android.domain.repository.AuthRepository
import javax.inject.Inject

class VerifyOTPUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend fun execute(phoneNumber: Int, otp: Int): OTPResponse {
        return authRepository.verifyOTP(OTPRequest(phoneNumber))
    }
}