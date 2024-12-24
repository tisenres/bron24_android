package com.bron24.bron24_android.helper.util

import com.bron24.bron24_android.data.local.preference.LocalStorage
import com.bron24.bron24_android.data.network.apiservices.AuthApi
import com.bron24.bron24_android.data.network.dto.auth.RefreshTokenDto
import com.bron24.bron24_android.domain.repository.AuthRepository
import com.bron24.bron24_android.domain.repository.TokenRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class AuthAuthenticator @Inject constructor(
    private val authApi: AuthApi,
    private val tokenRepository: TokenRepository,
    private val appRepository: AuthRepository,
) : Authenticator {
    companion object {
        const val HEADER_AUTHORIZATION = "Authorization"
        const val TOKEN_TYPE = "Bearer"
    }

    override fun authenticate(route: Route?, response: Response): Request? {
        val currentToken = runBlocking { tokenRepository.getAccessToken() }
        synchronized(this) {
            val updatedToken = runBlocking { tokenRepository.getAccessToken() }
            val token = if (currentToken != updatedToken) updatedToken else {
                val newSessionResponse = runBlocking { authApi.updateToken(RefreshTokenDto(tokenRepository.getRefreshToken()?:"")) }
                if (newSessionResponse.isSuccessful && newSessionResponse.body() != null) {
                    newSessionResponse.body()!!
                    newSessionResponse.body()?.let { body ->
                        runBlocking {
                            tokenRepository.saveTokens( accessToken = body.data?.accessToken?:"", refreshToken = body.data?.refreshToken?:"")
                        }
                        body.data?.accessToken
                    }
                } else null
            }
            if (token == null) {
                runBlocking {
                    //appRepository.logOut()
                }
            }
            return if (token != null) response.request.newBuilder()
                .header(HEADER_AUTHORIZATION, "$TOKEN_TYPE $token")
                .build() else null
        }
    }
}