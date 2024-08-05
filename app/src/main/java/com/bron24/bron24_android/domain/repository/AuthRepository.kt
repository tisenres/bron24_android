package com.bron24.bron24_android.domain.repository

import com.bron24.bron24_android.domain.entity.auth.OTPRequest
import com.bron24.bron24_android.domain.entity.auth.OTPResponse

interface AuthRepository {
    suspend fun requestOTP(otpRequest: OTPRequest): OTPResponse
    suspend fun verifyOTP(otpRequest: OTPRequest): OTPResponse
}