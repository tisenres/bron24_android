package com.bron24.bron24_android.domain.repository

import com.bron24.bron24_android.domain.entity.auth.OTPCodeResponseEntity
import com.bron24.bron24_android.domain.entity.auth.OTPRequestEntity
import com.bron24.bron24_android.domain.entity.auth.PhoneNumberResponseEntity

interface AuthRepository {
    suspend fun requestOTP(otpRequest: OTPRequestEntity): PhoneNumberResponseEntity
    suspend fun verifyOTP(otpRequest: OTPRequestEntity): OTPCodeResponseEntity
}