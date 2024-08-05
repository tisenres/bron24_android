package com.bron24.bron24_android.domain.repository

interface TokenRepository {
    fun saveToken(token: String, expiry: Long)
    fun getToken(): String?
    fun getTokenExpiry(): Long
    fun clearToken()
    fun isTokenExpired(): Boolean
}
