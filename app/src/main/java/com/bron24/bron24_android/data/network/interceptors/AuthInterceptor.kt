package com.bron24.bron24_android.data.network.interceptors

import com.bron24.bron24_android.domain.repository.AuthRepository
import com.bron24.bron24_android.domain.repository.TokenRepository
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import kotlinx.coroutines.runBlocking

class AuthInterceptor @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val authRepository: AuthRepository
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var accessToken = tokenRepository.getAccessToken()
        val refreshToken = tokenRepository.getRefreshToken()

        if (accessToken == null || tokenRepository.isAccessTokenExpired()) {
            synchronized(this) {
                runBlocking {
                    try {
                        val newTokens = refreshToken?.let {
                            authRepository.refreshAccessToken(it)
                        }
                        if (newTokens != null) {
                            tokenRepository.saveTokens(
                                newTokens.accessToken,
                                newTokens.refreshToken,
                                System.currentTimeMillis() + newTokens.accessExpiresAt * 1000,
                                System.currentTimeMillis() + newTokens.refreshExpiresAt * 1000
                            )
                            accessToken = newTokens.accessToken
                        }
                    } catch (e: Exception) {
                        // Log the error and possibly trigger a logout or error state in the app
                        // e.g., log.error("Failed to refresh token", e)
                    }
                }
            }
        }

        val authenticatedRequest = accessToken?.let {
            chain.request().newBuilder()
                .header("Authorization", "Bearer $it")
                .build()
        } ?: chain.request()

        return chain.proceed(authenticatedRequest)
    }
}