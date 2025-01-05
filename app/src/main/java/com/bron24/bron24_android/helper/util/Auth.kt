package com.bron24.bron24_android.helper.util

import android.util.Log
import com.bron24.bron24_android.data.local.preference.LocalStorage
import com.bron24.bron24_android.data.network.apiservices.AuthApi
import com.bron24.bron24_android.data.network.dto.auth.AuthDataDto
import com.bron24.bron24_android.data.network.dto.auth.AuthResponseDto
import com.bron24.bron24_android.data.network.dto.auth.RefreshTokenDto
import com.bron24.bron24_android.data.network.dto.auth.UserDto
import com.bron24.bron24_android.domain.repository.AuthRepository
import com.bron24.bron24_android.domain.repository.TokenRepository
import com.bron24.bron24_android.helper.util.presentation.listener
import com.bron24.bron24_android.helper.util.presentation.logOut
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
                val newSessionResponse = runBlocking {
                    try {
                        authApi.updateToken(RefreshTokenDto(tokenRepository.getRefreshToken()?:""))
                    }catch (e:Exception){
                        AuthResponseDto(false,"", data = AuthDataDto(tokenRepository.getRefreshToken()?:"","",null),)
                    }
                }
                if(newSessionResponse.success){
                    newSessionResponse.data?.let {
                        runBlocking {
                            tokenRepository.saveTokens( accessToken =it.accessToken?:"", refreshToken = it.refreshToken?:"")
                        }
                        it.accessToken
                    }
                }else null

            }
            if (token == null) {
                runBlocking {
                    listener?.invoke()
                }
            }
            return if (token != null) response.request.newBuilder()
                .header(HEADER_AUTHORIZATION, "$TOKEN_TYPE $token")
                .build() else null
        }
    }
}