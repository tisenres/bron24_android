package com.bron24.bron24_android.screens.auth

import com.bron24.bron24_android.domain.entity.auth.OTPCodeResponseEntity
import com.bron24.bron24_android.domain.entity.auth.PhoneNumberResponseEntity
import com.bron24.bron24_android.domain.usecases.auth.*
import javax.inject.Inject

class AuthModel @Inject constructor(
    private val requestOTPUseCase: RequestOTPUseCase,
    private val verifyAndStoreOTPUseCase: VerifyAndStoreOTPUseCase,
    private val isTokenExpiredUseCase: IsTokenExpiredUseCase,
    private val clearTokenUseCase: ClearTokenUseCase
) {
    suspend fun requestOTP(phoneNumber: String): PhoneNumberResponseEntity {
        return requestOTPUseCase.execute(phoneNumber)
    }

    suspend fun verifyOTP(phoneNumber: String, otp: Int): OTPCodeResponseEntity {
        return verifyAndStoreOTPUseCase.execute(phoneNumber, otp)
    }

    fun isTokenExpired(): Boolean {
        return isTokenExpiredUseCase.execute()
    }

    fun clearToken() {
        clearTokenUseCase.execute()
    }
}