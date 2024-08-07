package com.bron24.bron24_android.domain.repository

import com.bron24.bron24_android.domain.entity.auth.OTPRequestEntity
import com.bron24.bron24_android.domain.entity.auth.OTPResponseEntity

interface AuthRepository {
    suspend fun requestOTP(otpRequest: OTPRequestEntity): OTPResponseEntity
    suspend fun verifyOTP(otpRequest: OTPRequestEntity): OTPResponseEntity
}