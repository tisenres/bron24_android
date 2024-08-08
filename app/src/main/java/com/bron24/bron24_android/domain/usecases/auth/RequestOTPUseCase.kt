package com.bron24.bron24_android.domain.usecases.auth

import com.bron24.bron24_android.domain.entity.auth.OTPRequest
import com.bron24.bron24_android.domain.entity.auth.PhoneNumberResponse
import com.bron24.bron24_android.domain.repository.AuthRepository
import javax.inject.Inject

class RequestOTPUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend fun execute(phoneNumber: String): PhoneNumberResponse {
        val request = OTPRequest(phoneNumber)
        return authRepository.requestOTP(request)
    }
}
