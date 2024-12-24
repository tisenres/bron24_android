package com.bron24.bron24_android.data.network.apiservices

import com.bron24.bron24_android.data.network.dto.auth.OTPCodeResponseDto
import com.bron24.bron24_android.data.network.dto.auth.PhoneNumberResponseDto
import com.bron24.bron24_android.data.network.dto.auth.AuthResponseDto
import com.bron24.bron24_android.data.network.dto.auth.LoginUserDto
import com.bron24.bron24_android.data.network.dto.auth.OTPRequestDto
import com.bron24.bron24_android.data.network.dto.auth.RefreshTokenDto
import com.bron24.bron24_android.data.network.dto.auth.SignupUserDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("api/v1/auth/request_otp/")
    suspend fun requestOTP(@Body otpRequest: OTPRequestDto): PhoneNumberResponseDto

    @POST("api/v1/auth/verify_otp/")
    suspend fun verifyOTP(@Body otpRequest: OTPRequestDto): OTPCodeResponseDto

    @POST("api/v1/auth/login/")
    suspend fun loginUser(@Body user: LoginUserDto): AuthResponseDto

    @POST("api/v1/auth/signup/")
    suspend fun signupUser(@Body user: SignupUserDto): AuthResponseDto

    @POST("api/v1/auth/token/refresh/")
    suspend fun updateToken(@Body refreshToken: RefreshTokenDto): Response<AuthResponseDto>
}