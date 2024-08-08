package com.bron24.bron24_android.domain.usecases.auth

import com.bron24.bron24_android.domain.repository.TokenRepository
import javax.inject.Inject

class SaveTokenUseCase @Inject constructor(private val tokenRepository: TokenRepository) {
    fun execute(accessToken: String, refreshToken: String, accessTokenExpiry: Long, refreshTokenExpiry: Long) {
        tokenRepository.saveTokens(accessToken, refreshToken, accessTokenExpiry, refreshTokenExpiry)
    }
}