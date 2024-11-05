package com.bron24.bron24_android.domain.usecases.auth

import com.bron24.bron24_android.domain.entity.auth.AuthResponse
import com.bron24.bron24_android.domain.entity.enums.OnboardingScreen
import com.bron24.bron24_android.domain.entity.user.User
import com.bron24.bron24_android.domain.repository.AuthRepository
import com.bron24.bron24_android.domain.repository.PreferencesRepository
import com.bron24.bron24_android.domain.repository.TokenRepository
import javax.inject.Inject

class AuthenticateUserUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenRepository: TokenRepository,
    private val preferencesRepository: PreferencesRepository
) {

    suspend fun execute(user: User, userExists: Boolean): AuthResponse {

        val response = if (userExists) {
            authRepository.loginUser(user)
        } else {
            authRepository.signUpUser(user)
        }
        if (response.accessToken.isNotBlank() && response.refreshToken.isNotBlank()) {
            tokenRepository.saveTokens(response.accessToken, response.refreshToken)
            preferencesRepository.setOnboardingCompleted(OnboardingScreen.AUTHENTICATION, true)
            if (response.firstName.isNotBlank() && response.lastName.isNotBlank()) {
                preferencesRepository.saveUserData(user.phoneNumber, response.firstName, response.lastName)
            } else {
                preferencesRepository.saveUserData(user.phoneNumber, user.firstName, user.lastName)
            }
        }
        return response
    }
}