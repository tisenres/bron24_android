package com.bron24.bron24_android.data.network.apiservices

import com.bron24.bron24_android.data.network.dto.auth.OTPCodeResponse
import com.bron24.bron24_android.data.network.dto.auth.OTPRequest
import com.bron24.bron24_android.data.network.dto.auth.PhoneNumberResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface OTPApiService {
    @POST("api/v1/auth/request_otp/")
    suspend fun requestOTP(@Body phoneNumber: OTPRequest): PhoneNumberResponse

    @POST("api/v1/auth/verify_otp/")
    suspend fun verifyOTP(@Body otpRequest: OTPRequest): OTPCodeResponse
}