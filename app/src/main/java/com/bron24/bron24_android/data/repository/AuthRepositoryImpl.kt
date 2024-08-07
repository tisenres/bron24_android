package com.bron24.bron24_android.data.repository

import android.util.Log
import com.bron24.bron24_android.data.network.apiservices.OTPApiService
import com.bron24.bron24_android.data.network.mappers.toDomainEntity
import com.bron24.bron24_android.data.network.mappers.toNetworkModel
import com.bron24.bron24_android.domain.entity.auth.OTPCodeResponseEntity
import com.bron24.bron24_android.domain.entity.auth.OTPRequestEntity
import com.bron24.bron24_android.domain.entity.auth.PhoneNumberResponseEntity
import com.bron24.bron24_android.domain.entity.enums.OTPStatusCode
import com.bron24.bron24_android.domain.repository.AuthRepository
import com.bron24.bron24_android.domain.repository.TokenRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val otpService: OTPApiService,
    private val tokenRepository: TokenRepository
) : AuthRepository {

    override suspend fun requestOTP(otpRequest: OTPRequestEntity): PhoneNumberResponseEntity {
        val networkRequest = otpRequest.toNetworkModel()
        val networkResponse = otpService.requestOTP(networkRequest)
        return networkResponse.toDomainEntity()
    }

    override suspend fun verifyOTP(otpRequest: OTPRequestEntity): OTPCodeResponseEntity {
        val networkRequest = otpRequest.toNetworkModel()
        val networkResponse = otpService.verifyOTP(networkRequest)
        val domainResponse = networkResponse.toDomainEntity()

        if (domainResponse.status == OTPStatusCode.SUCCESS) {
            val expiryTime = System.currentTimeMillis() + 90L * 24 * 60 * 60 * 1000 // 90 days in milliseconds
//            tokenRepository.saveToken(domainResponse.status ?: "", expiryTime)
        }
        return domainResponse
    }
}
