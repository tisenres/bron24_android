package com.bron24.bron24_android.data.repository

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

    init {
        loadTokensFromPreferences()
    }

    private fun loadTokensFromPreferences() {
        cachedAccessToken = appPreference.getAccessToken()
        cachedRefreshToken = appPreference.getRefreshToken()
    }

    override fun saveTokens(accessToken: String, refreshToken: String) {
        cachedAccessToken = accessToken
        cachedRefreshToken = refreshToken
        appPreference.saveTokens(accessToken, refreshToken)
    }

    override fun getAccessToken(): String? {
        return cachedAccessToken
    }

    override fun getRefreshToken(): String? {
        return cachedRefreshToken
    }

    override fun clearTokens() {
        cachedAccessToken = null
        cachedRefreshToken = null
        appPreference.clearTokens()
    }
}