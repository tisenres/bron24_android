package com.bron24.bron24_android.domain.usecases.auth

import com.bron24.bron24_android.domain.entity.auth.OTPResponse
import javax.inject.Inject

class VerifyAndStoreOTPUseCase @Inject constructor(
    private val verifyOTPUseCase: VerifyOTPUseCase,
    private val saveTokenUseCase: SaveTokenUseCase
) {
    suspend fun execute(phoneNumber: String, otp: String): OTPResponse {
        val response = verifyOTPUseCase.execute(phoneNumber, otp)
        if (response.success && response.token != null) {
            val expiryTime = System.currentTimeMillis() + 90L * 24 * 60 * 60 * 1000
            saveTokenUseCase.execute(response.token, expiryTime)
        }
        return response
    }
}
