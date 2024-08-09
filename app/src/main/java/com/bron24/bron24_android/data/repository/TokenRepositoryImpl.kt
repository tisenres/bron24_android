package com.bron24.bron24_android.data.repository

import android.util.Log
import com.bron24.bron24_android.data.local.preference.AppPreference
import com.bron24.bron24_android.domain.repository.TokenRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenRepositoryImpl @Inject constructor(
    private val appPreference: AppPreference
) : TokenRepository {

    private var cachedAccessToken: String? = null
    private var cachedRefreshToken: String? = null
    private var cachedAccessTokenExpiry: Long = 0
    private var cachedRefreshTokenExpiry: Long = 0

    init {
        loadTokensFromPreferences()
    }

    private fun loadTokensFromPreferences() {
        cachedAccessToken = appPreference.getAccessToken()
        cachedRefreshToken = appPreference.getRefreshToken()
        cachedAccessTokenExpiry = appPreference.getAccessTokenExpiry()
        cachedRefreshTokenExpiry = appPreference.getRefreshTokenExpiry()
    }

    override fun saveTokens(accessToken: String, refreshToken: String, accessTokenExpiry: Long, refreshTokenExpiry: Long) {
        cachedAccessToken = accessToken
        cachedRefreshToken = refreshToken
        cachedAccessTokenExpiry = accessTokenExpiry
        cachedRefreshTokenExpiry = refreshTokenExpiry
        appPreference.saveTokens(accessToken, refreshToken, accessTokenExpiry, refreshTokenExpiry)
    }

    override fun getAccessToken(): String? {
        Log.d("SDHJSJDHHSJHDHJDS", "access$cachedRefreshToken")
        return cachedAccessToken
    }

    override fun getRefreshToken(): String? {
        Log.d("SDHJSJDHHSJHDHJDS", "refresh$cachedRefreshToken")
        return cachedRefreshToken
    }

    override fun getAccessTokenExpiry(): Long {
        return cachedAccessTokenExpiry
    }

    override fun getRefreshTokenExpiry(): Long {
        return cachedRefreshTokenExpiry
    }

    override fun clearTokens() {
        cachedAccessToken = null
        cachedRefreshToken = null
        cachedAccessTokenExpiry = 0
        cachedRefreshTokenExpiry = 0
        appPreference.clearTokens()
    }

    override fun isAccessTokenExpired(): Boolean {
        return System.currentTimeMillis() > cachedAccessTokenExpiry
    }

    override fun isRefreshTokenExpired(): Boolean {
        return System.currentTimeMillis() > cachedRefreshTokenExpiry
    }
}