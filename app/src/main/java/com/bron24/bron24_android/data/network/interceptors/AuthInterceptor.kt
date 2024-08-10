package com.bron24.bron24_android.data.network.interceptors

import android.util.Log
import com.bron24.bron24_android.domain.repository.AuthRepository
import com.bron24.bron24_android.domain.repository.TokenRepository
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import kotlinx.coroutines.runBlocking
import dagger.Lazy

class AuthInterceptor @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val authRepository: Lazy<AuthRepository>  // Use Lazy to defer injection
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var accessToken = tokenRepository.getAccessToken()
        val refreshToken = tokenRepository.getRefreshToken()

        Log.d("AuthInterceptor", "RefreshToken: $refreshToken, AccessToken: $accessToken")

        if (accessToken == null || tokenRepository.isAccessTokenExpired()) {
            synchronized(this) {
                runBlocking {
                    try {
                        refreshToken?.let {
                            val newTokens = authRepository.get().refreshAccessToken(it)  // Lazy inject here
                            tokenRepository.saveTokens(
                                newTokens.accessToken,
                                newTokens.refreshToken,
                                System.currentTimeMillis() + newTokens.accessExpiresAt * 1000,
                                System.currentTimeMillis() + newTokens.refreshExpiresAt * 1000
                            )
                            accessToken = newTokens.accessToken
                        }
                    } catch (e: Exception) {
                        Log.e("com.bron24.bron24_android.data.network.interceptors.AuthInterceptor", "Token refresh failed", e)
                        // Handle error, e.g., log out the user
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