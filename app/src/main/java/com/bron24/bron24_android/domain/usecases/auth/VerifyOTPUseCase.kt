package com.bron24.bron24_android.domain.usecases.auth

import com.bron24.bron24_android.domain.entity.auth.OTPRequestEntity
import com.bron24.bron24_android.domain.entity.auth.OTPResponseEntity
import com.bron24.bron24_android.domain.repository.AuthRepository
import javax.inject.Inject

class VerifyOTPUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend fun execute(phoneNumber: String, otp: Int): OTPResponseEntity {
        val request = OTPRequestEntity(phoneNumber, otp)
        return authRepository.verifyOTP(request)
    }
}