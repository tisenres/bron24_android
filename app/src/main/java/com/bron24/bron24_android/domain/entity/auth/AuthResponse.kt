package com.bron24.bron24_android.domain.entity.auth

data class AuthResponse(
    val accessToken: String,
    val refreshToken: String,
    val firstName: String,
    val lastName: String
)