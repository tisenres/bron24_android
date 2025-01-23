package com.bron24.bron24_android.data.repository

import android.util.Log
import androidx.compose.ui.input.key.Key.Companion.U
import com.bron24.bron24_android.data.network.apiservices.AuthApi
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
import com.bron24.bron24_android.helper.util.presentation.AuthEvent
import com.bron24.bron24_android.helper.util.presentation.GlobalAuthEventBus
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val tokenRepository: TokenRepository
) : AuthRepository {

    override suspend fun requestOTP(otpRequest: OTPRequest): PhoneNumberResponse {
        return try {
            val networkRequest = otpRequest.toNetworkModel()
            val networkResponse = authApi.requestOTP(networkRequest)
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
            val networkResponse = authApi.verifyOTP(networkRequest)
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
            val networkResponse = authApi.signupUser(networkRequest)
            networkResponse.toDomainEntity()
        } catch (e: HttpException) {
            handleHttpExceptionAuth(e)
        }
    }

    override suspend fun loginUser(user: User): AuthResponse {
        return try {
            val networkRequest = user.toLoginNetworkModel()
            val networkResponse = authApi.loginUser(networkRequest)
            networkResponse.toDomainEntity()
        } catch (e: HttpException) {
            handleHttpExceptionAuth(e)
        }
    }


    private fun handleHttpExceptionPhone(e: HttpException): PhoneNumberResponse {
        return when (e.code()) {
            429 -> {
                Log.d("AuthRepository", "429 error")
                PhoneNumberResponse(PhoneNumberResponseStatusCode.MANY_REQUESTS)
            }
            400 -> PhoneNumberResponse(PhoneNumberResponseStatusCode.INCORRECT_PHONE_NUMBER)
            else -> PhoneNumberResponse(PhoneNumberResponseStatusCode.UNKNOWN_ERROR)
        }
    }

    private fun handleHttpExceptionOTP(e: HttpException): OTPCodeResponse {
        return when (e.code()) {
            400 -> {
                OTPCodeResponse(OTPStatusCode.INCORRECT_OTP, false)
            }
            429-> {
                OTPCodeResponse(OTPStatusCode.BANNED_USER,false)
            }
            else -> OTPCodeResponse(OTPStatusCode.UNKNOWN_ERROR, false)
        }
    }

    override fun handleRefreshFailure() {
        Log.e("AuthRepository", "handleRefreshFailure")
        tokenRepository.clearTokens()
        GlobalAuthEventBus.postEventBlocking(AuthEvent.TokenRefreshFailed)
    }

    private fun handleHttpExceptionAuth(e: HttpException): AuthResponse {
        return when (e.code()) {
            401 -> {
                Log.d("AuthRepository", "401 error")
                handleRefreshFailure()
                AuthResponse("", "", "", "")
            }
            else -> {
                Log.d("AuthRepository", "Unexpected error: ${e.code()}")
                AuthResponse("", "", "", "")
            }
        }
    }
}