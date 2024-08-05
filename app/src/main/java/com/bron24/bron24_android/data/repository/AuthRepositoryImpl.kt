package com.bron24.bron24_android.data.repository

import com.bron24.bron24_android.data.local.preference.AppPreference
import com.bron24.bron24_android.data.network.apiservices.OTPApiService
import com.bron24.bron24_android.domain.entity.auth.OTPRequest
import com.bron24.bron24_android.domain.entity.auth.OTPResponse
import com.bron24.bron24_android.domain.repository.AuthRepository
import com.bron24.bron24_android.domain.repository.TokenRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val otpService: OTPApiService,
    private val tokenRepository: TokenRepository
) : AuthRepository {

    override suspend fun requestOTP(otpRequest: OTPRequest): OTPResponse {
        return otpService.requestOTP(otpRequest)
    }

    override suspend fun verifyOTP(otpRequest: OTPRequest): OTPResponse {
        val response = otpService.verifyOTP(otpRequest)
        if (response.success && response.token != null) {
            val expiryTime = System.currentTimeMillis() + 90L * 24 * 60 * 60 * 1000 // 90 days in milliseconds
            tokenRepository.saveToken(response.token, expiryTime)
        }
        return response
    }
}

