package com.bron24.bron24_android.data.repository

import com.bron24.bron24_android.data.local.preference.AppPreference
import com.bron24.bron24_android.domain.repository.TokenRepository
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val preferenceManager: AppPreference
) : TokenRepository {

    override fun saveToken(token: String, expiry: Long) {
        preferenceManager.saveToken(token, expiry)
    }

    override fun getToken(): String? {
        return preferenceManager.getToken()
    }

    override fun getTokenExpiry(): Long {
        return preferenceManager.getTokenExpiry()
    }

    override fun clearToken() {
        preferenceManager.clearToken()
    }

    override fun isTokenExpired(): Boolean {
        val expiry = preferenceManager.getTokenExpiry()
        return System.currentTimeMillis() > expiry
    }
}