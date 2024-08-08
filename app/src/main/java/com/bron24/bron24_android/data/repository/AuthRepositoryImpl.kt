package com.bron24.bron24_android.data.repository

import com.bron24.bron24_android.data.network.apiservices.AuthApiService
import com.bron24.bron24_android.data.network.mappers.toDomainEntity
import com.bron24.bron24_android.data.network.mappers.toNetworkModel
import com.bron24.bron24_android.domain.entity.auth.AuthResponse
import com.bron24.bron24_android.domain.entity.auth.OTPCodeResponse
import com.bron24.bron24_android.domain.entity.auth.OTPRequest
import com.bron24.bron24_android.domain.entity.auth.PhoneNumberResponse
import com.bron24.bron24_android.domain.entity.user.User
import com.bron24.bron24_android.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApiService: AuthApiService
) : AuthRepository {

    override suspend fun requestOTP(otpRequest: OTPRequest): PhoneNumberResponse {
        val networkRequest = otpRequest.toNetworkModel()
        val networkResponse = authApiService.requestOTP(networkRequest)
        return networkResponse.toDomainEntity()
    }

    override suspend fun verifyOTP(otpRequest: OTPRequest): OTPCodeResponse {
        val networkRequest = otpRequest.toNetworkModel()
        val networkResponse = authApiService.verifyOTP(networkRequest)
        return networkResponse.toDomainEntity()
    }

    override suspend fun authenticateUser(user: User): AuthResponse {
        val networkRequest = user.toNetworkModel()
        val networkResponse = authApiService.authenticateUser(networkRequest)
        return networkResponse.toDomainEntity()
    }

    override suspend fun refreshAccessToken(refreshToken: String): AuthResponse {
        val networkResponse = authApiService.refreshAccessToken(refreshToken)
        return networkResponse.toDomainEntity()
    }
}