package com.bron24.bron24_android.domain.usecases.auth

import com.bron24.bron24_android.domain.entity.auth.AuthResponse
import com.bron24.bron24_android.domain.entity.user.User
import com.bron24.bron24_android.domain.repository.AuthRepository
import com.bron24.bron24_android.domain.repository.TokenRepository
import javax.inject.Inject

class AuthenticateUserUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenRepository: TokenRepository
) {
    suspend fun execute(user: User): AuthResponse {
        val response = authRepository.authenticateUser(user)
        if (response.accessToken.isNotBlank() && response.refreshToken.isNotBlank()) {
            tokenRepository.saveTokens(response.accessToken, response.refreshToken, response.accessExpiresAt, response.refreshExpiresAt)
        }
        return response
    }
}