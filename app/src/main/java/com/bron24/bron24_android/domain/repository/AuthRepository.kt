package com.bron24.bron24_android.domain.repository

import com.bron24.bron24_android.domain.entity.auth.AuthResponse
import com.bron24.bron24_android.domain.entity.auth.OTPCodeResponse
import com.bron24.bron24_android.domain.entity.auth.OTPRequest
import com.bron24.bron24_android.domain.entity.auth.PhoneNumberResponse
import com.bron24.bron24_android.domain.entity.auth.enums.OTPStatusCode
import com.bron24.bron24_android.domain.entity.user.UpdateProfile
import com.bron24.bron24_android.domain.entity.user.User

interface AuthRepository {
    suspend fun requestOTP(otpRequest: OTPRequest): PhoneNumberResponse
    suspend fun verifyOTP(otpRequest: OTPRequest): OTPCodeResponse
    suspend fun signUpUser(user: User): AuthResponse
    suspend fun loginUser(user: User): AuthResponse
    //suspend fun refreshAndSaveTokens(refreshToken: String): Boolean
    fun handleRefreshFailure()
}