package com.bron24.bron24_android.domain.usecases.auth

import com.bron24.bron24_android.data.local.preference.LocalStorage
import com.bron24.bron24_android.domain.entity.auth.AuthResponse
import com.bron24.bron24_android.domain.entity.enums.OnboardingScreen
import com.bron24.bron24_android.domain.entity.user.User
import com.bron24.bron24_android.domain.repository.AuthRepository
import com.bron24.bron24_android.domain.repository.PreferencesRepository
import com.bron24.bron24_android.domain.repository.TokenRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AuthenticateUserUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenRepository: TokenRepository,
    private val preferencesRepository: PreferencesRepository,
    private val localStorage: LocalStorage
) {

    operator fun invoke(user: User, userExists: Boolean): Flow<Result<Unit>> = flow {
        val response = if (userExists) {
            authRepository.loginUser(user)
        } else {
            authRepository.signUpUser(user)
        }
        if (response.accessToken.isNotBlank() && response.refreshToken.isNotBlank()) {
            tokenRepository.saveTokens(response.accessToken, response.refreshToken)
            preferencesRepository.setOnboardingCompleted(OnboardingScreen.AUTHENTICATION, true)
            if (response.firstName.isNotBlank() && response.lastName.isNotBlank()) {
                localStorage.firstName = response.firstName
                localStorage.lastName = response.lastName
                localStorage.phoneNumber = user.phoneNumber
            } else {
                localStorage.firstName = user.firstName
                localStorage.lastName = user.lastName
                localStorage.phoneNumber = user.phoneNumber
            }
        }
        emit(Result.success(Unit))
    }.catch { emit(Result.failure(it)) }.flowOn(Dispatchers.IO)
}