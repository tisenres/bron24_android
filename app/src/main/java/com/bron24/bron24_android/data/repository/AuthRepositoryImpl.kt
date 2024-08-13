package com.bron24.bron24_android.data.repository

import com.bron24.bron24_android.data.network.apiservices.AuthApiService
import com.bron24.bron24_android.data.network.mappers.toDomainEntity
import com.bron24.bron24_android.data.network.mappers.toNetworkModel
import com.bron24.bron24_android.domain.entity.auth.AuthResponse
import com.bron24.bron24_android.domain.entity.auth.OTPCodeResponse
import com.bron24.bron24_android.domain.entity.auth.OTPRequest
import com.bron24.bron24_android.domain.entity.auth.PhoneNumberResponse
import com.bron24.bron24_android.domain.entity.auth.enums.OTPStatusCode
import com.bron24.bron24_android.domain.entity.auth.enums.PhoneNumberResponseStatusCode
import com.bron24.bron24_android.domain.entity.user.User
import com.bron24.bron24_android.domain.repository.AuthRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApiService: AuthApiService
) : AuthRepository {

    override suspend fun requestOTP(otpRequest: OTPRequest): PhoneNumberResponse {
        return try {
            val networkRequest = otpRequest.toNetworkModel()
            val networkResponse = authApiService.requestOTP(networkRequest)
            networkResponse.toDomainEntity()
        } catch (e: HttpException) {
            handleHttpExceptionPhone(e)
        } catch (e: IOException) {
            // Handle network errors or IO exceptions here
            PhoneNumberResponse(PhoneNumberResponseStatusCode.NETWORK_ERROR)
        }
    }

    override suspend fun verifyOTP(otpRequest: OTPRequest): OTPCodeResponse {
        return try {
            val networkRequest = otpRequest.toNetworkModel()
            val networkResponse = authApiService.verifyOTP(networkRequest)
            networkResponse.toDomainEntity()
        } catch (e: HttpException) {
            handleHttpExceptionOTP(e)
        } catch (e: IOException) {
            // Handle network errors or IO exceptions here
            OTPCodeResponse(OTPStatusCode.NETWORK_ERROR)
        }
    }

    override suspend fun authenticateUser(user: User): AuthResponse {
        return try {
            val networkRequest = user.toNetworkModel()
            val networkResponse = authApiService.authenticateUser(networkRequest)
            networkResponse.toDomainEntity()
        } catch (e: HttpException) {
            handleHttpExceptionAuth(e)
        }
    }

    override suspend fun refreshAccessToken(refreshToken: String): AuthResponse {
        return try {
            val networkResponse = authApiService.refreshAccessToken(refreshToken)
            networkResponse.toDomainEntity()
        } catch (e: HttpException) {
            handleHttpExceptionAuth(e)
        }
    }

    private fun handleHttpExceptionPhone(e: HttpException): PhoneNumberResponse {
        return when (e.code()) {
            429 -> PhoneNumberResponse(PhoneNumberResponseStatusCode.MANY_REQUESTS)
            400 -> PhoneNumberResponse(PhoneNumberResponseStatusCode.INCORRECT_PHONE_NUMBER)
            else -> PhoneNumberResponse(PhoneNumberResponseStatusCode.UNKNOWN_ERROR)
        }
    }

    private fun handleHttpExceptionOTP(e: HttpException): OTPCodeResponse {
        return when (e.code()) {
            400 -> OTPCodeResponse(OTPStatusCode.INCORRECT_OTP)
            else -> OTPCodeResponse(OTPStatusCode.UNKNOWN_ERROR)
        }
    }

    private fun handleHttpExceptionAuth(e: HttpException): AuthResponse {
        // Handle different HTTP errors and map them to AuthResponse
        return AuthResponse(
            accessToken = "",
            refreshToken = "",
            accessExpiresAt = 0L,
            refreshExpiresAt = 0L
        )
    }
}