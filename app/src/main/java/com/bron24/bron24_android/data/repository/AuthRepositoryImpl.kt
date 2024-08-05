package com.bron24.bron24_android.data.repository

import com.bron24.bron24_android.data.local.preference.AppPreference
import com.bron24.bron24_android.data.network.apiservices.OTPApiService
import com.bron24.bron24_android.domain.entity.auth.OTPRequest
import com.bron24.bron24_android.domain.entity.auth.OTPResponse
import com.bron24.bron24_android.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val otpService: OTPApiService,
    private val preferenceManager: AppPreference
) : AuthRepository {

    override suspend fun requestOTP(otpRequest: OTPRequest): OTPResponse {
        return otpService.requestOTP(otpRequest)
    }

    override suspend fun verifyOTP(otpRequest: OTPRequest): OTPResponse {
        val response = otpService.verifyOTP(otpRequest)
        if (response.success && response.token != null) {
            preferenceManager.saveToken(response.token)
        }
        return response
    }
}
