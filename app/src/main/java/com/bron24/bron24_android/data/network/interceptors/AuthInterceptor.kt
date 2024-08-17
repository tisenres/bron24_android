package com.bron24.bron24_android.data.network.interceptors

import android.util.Log
import com.bron24.bron24_android.domain.repository.AuthRepository
import com.bron24.bron24_android.domain.repository.TokenRepository
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject
import kotlinx.coroutines.runBlocking
import dagger.Lazy

class AuthInterceptor @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val authRepository: Lazy<AuthRepository>
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val accessToken = tokenRepository.getAccessToken()
        val refreshToken = tokenRepository.getRefreshToken()

        Log.d("AuthInterceptor", "RefreshToken: $refreshToken, AccessToken: $accessToken")

        // Add Authorization header if accessToken is available
        val initialRequest = addAuthHeader(originalRequest, accessToken)
        val response = chain.proceed(initialRequest)

        if ((response.code == 401 || response.code == 404) && !refreshToken.isNullOrEmpty()) {
            response.close()

            synchronized(this) {
                val currentAccessToken = tokenRepository.getAccessToken()
                if (currentAccessToken != accessToken) {
                    return chain.proceed(addAuthHeader(originalRequest, currentAccessToken))
                }

                val refreshedSuccessfully = runBlocking {
                    authRepository.get().refreshAndSaveTokens(refreshToken)
                }

                if (refreshedSuccessfully) {
                    val newAccessToken = tokenRepository.getAccessToken()
                    return chain.proceed(addAuthHeader(originalRequest, newAccessToken))
                } else {
                    Log.e("AuthInterceptor", "Failed to refresh token")
                    return response
                }
            }
        }

        return response
    }

    private fun addAuthHeader(request: Request, token: String?): Request {
        return token?.let {
            request.newBuilder()
                .header("Authorization", "Bearer $it")
                .build()
        } ?: request
    }
}