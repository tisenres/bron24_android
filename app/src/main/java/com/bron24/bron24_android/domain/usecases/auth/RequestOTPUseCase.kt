package com.bron24.bron24_android.domain.usecases.auth

import com.bron24.bron24_android.domain.entity.auth.OTPRequestEntity
import com.bron24.bron24_android.domain.entity.auth.PhoneNumberResponseEntity
import com.bron24.bron24_android.domain.repository.AuthRepository
import javax.inject.Inject

class RequestOTPUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend fun execute(phoneNumber: String): PhoneNumberResponseEntity {
        val request = OTPRequestEntity(phoneNumber)
        return authRepository.requestOTP(request)
    }
}
