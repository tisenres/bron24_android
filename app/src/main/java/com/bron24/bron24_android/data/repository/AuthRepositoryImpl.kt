package com.bron24.bron24_android.data.repository

import com.bron24.bron24_android.data.network.apiservices.OTPApiService
import com.bron24.bron24_android.data.network.mappers.toDomainEntity
import com.bron24.bron24_android.data.network.mappers.toNetworkModel
import com.bron24.bron24_android.domain.entity.auth.OTPRequestEntity
import com.bron24.bron24_android.domain.entity.auth.OTPResponseEntity
import com.bron24.bron24_android.domain.repository.AuthRepository
import com.bron24.bron24_android.domain.repository.TokenRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val otpService: OTPApiService,
    private val tokenRepository: TokenRepository
) : AuthRepository {

    override suspend fun requestOTP(otpRequest: OTPRequestEntity): OTPResponseEntity {
        val networkRequest = otpRequest.toNetworkModel()
        val networkResponse = otpService.requestOTP(networkRequest)
        return networkResponse.toDomainEntity()
    }

    override suspend fun verifyOTP(otpRequest: OTPRequestEntity): OTPResponseEntity {
        val networkRequest = otpRequest.toNetworkModel()
        val networkResponse = otpService.verifyOTP(networkRequest)
        val domainResponse = networkResponse.toDomainEntity()

        if (domainResponse.success == true && domainResponse.access != null) {
            val expiryTime = System.currentTimeMillis() + 90L * 24 * 60 * 60 * 1000 // 90 days in milliseconds
            tokenRepository.saveToken(domainResponse.access, expiryTime)
        }
        return domainResponse
    }
}