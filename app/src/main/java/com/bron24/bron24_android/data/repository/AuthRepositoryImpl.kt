package com.bron24.bron24_android.data.repository

import android.util.Log
import com.bron24.bron24_android.data.network.apiservices.AuthApiService
import com.bron24.bron24_android.data.network.dto.auth.AuthResponseDto
import com.bron24.bron24_android.data.network.dto.auth.RefreshTokenDto
import com.bron24.bron24_android.data.network.mappers.toDomainEntity
import com.bron24.bron24_android.data.network.mappers.toLoginNetworkModel
import com.bron24.bron24_android.data.network.mappers.toNetworkModel
import com.bron24.bron24_android.data.network.mappers.toSignupNetworkModel
import com.bron24.bron24_android.domain.entity.auth.AuthResponse
import com.bron24.bron24_android.domain.entity.auth.OTPCodeResponse
import com.bron24.bron24_android.domain.entity.auth.OTPRequest
import com.bron24.bron24_android.domain.entity.auth.PhoneNumberResponse
import com.bron24.bron24_android.domain.entity.auth.enums.OTPStatusCode
import com.bron24.bron24_android.domain.entity.auth.enums.PhoneNumberResponseStatusCode
import com.bron24.bron24_android.domain.entity.user.User
import com.bron24.bron24_android.domain.repository.AuthRepository
import com.bron24.bron24_android.domain.repository.TokenRepository
import com.bron24.bron24_android.screens.main.AuthEvent
import com.bron24.bron24_android.screens.main.GlobalAuthEventBus
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApiService: AuthApiService,
    private val tokenRepository: TokenRepository
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
            OTPCodeResponse(OTPStatusCode.NETWORK_ERROR, false)
        }
    }

    override suspend fun signUpUser(user: User): AuthResponse {
        return try {
            val networkRequest = user.toSignupNetworkModel()
            val networkResponse = authApiService.signupUser(networkRequest)
            networkResponse.toDomainEntity()
        } catch (e: HttpException) {
            handleHttpExceptionAuth(e)
        }
    }

    override suspend fun loginUser(user: User): AuthResponse {
        return try {
            val networkRequest = user.toLoginNetworkModel()
            val networkResponse = authApiService.loginUser(networkRequest)
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
            400 -> OTPCodeResponse(OTPStatusCode.INCORRECT_OTP, false)
            else -> OTPCodeResponse(OTPStatusCode.UNKNOWN_ERROR, false)
        }
    }

    private fun handleHttpExceptionAuth(e: HttpException): AuthResponse {
        // Handle different HTTP errors and map them to AuthResponse
        return AuthResponse(
            accessToken = "",
            refreshToken = "",
        )
    }

    override suspend fun refreshAndSaveTokens(refreshToken: String): Boolean {
        return try {
            val refreshTokenDto = RefreshTokenDto(refreshToken)
            val tokens = authApiService.refreshAccessToken(refreshTokenDto).toDomainEntity()

            if (tokens.accessToken.isNotEmpty() && tokens.refreshToken.isNotEmpty()) {
                tokenRepository.saveTokens(tokens.accessToken, tokens.refreshToken)
                true
            } else {
                handleRefreshFailure()
                false
            }

//            if (response.success && response.data != null) {
//                tokenRepository.saveTokens(response.data.accessToken, response.data.refreshToken)
//                true
//            } else {
//                handleRefreshFailure()
//                false
//            }
        } catch (e: Exception) {
            Log.e("AuthRepository", "Failed to refresh token", e)
            handleRefreshFailure()
            false
        }
    }

    private fun handleRefreshFailure() {
        Log.e("AuthRepository", "handleRefreshFailure")
        tokenRepository.clearTokens()
        GlobalAuthEventBus.postEventBlocking(AuthEvent.TokenRefreshFailed)
    }
}