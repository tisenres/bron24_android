package com.bron24.bron24_android.domain.repository

interface TokenRepository {
    fun saveTokens(
        accessToken: String,
        refreshToken: String,
        accessTokenExpiry: Long,
        refreshTokenExpiry: Long
    )
    fun getAccessToken(): String?
    fun getRefreshToken(): String?
    fun getAccessTokenExpiry(): Long
    fun getRefreshTokenExpiry(): Long
    fun clearTokens()
    fun isAccessTokenExpired(): Boolean
    fun isRefreshTokenExpired(): Boolean
}
