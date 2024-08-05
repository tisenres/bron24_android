package com.bron24.bron24_android.data.network.apiservices

import com.bron24.bron24_android.domain.entity.auth.OTPRequest
import com.bron24.bron24_android.domain.entity.auth.OTPResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface OTPApiService {
    @POST("/api/request-otp")
    suspend fun requestOTP(@Body request: OTPRequest): OTPResponse

    @POST("/api/verify-otp")
    suspend fun verifyOTP(@Body request: OTPRequest): OTPResponse
}