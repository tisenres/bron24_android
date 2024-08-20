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
    private var refreshFailed: Boolean = false

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
        refreshFailed = false  // Reset the flag when new tokens are saved
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
        refreshFailed = false  // Reset the flag when tokens are cleared
    }

    override fun setRefreshFailed(failed: Boolean) {
        refreshFailed = failed
    }

    override fun hasRefreshFailed(): Boolean {
        return refreshFailed
    }
}