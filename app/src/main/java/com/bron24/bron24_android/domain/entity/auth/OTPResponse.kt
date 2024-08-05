package com.bron24.bron24_android.domain.entity.auth

data class OTPResponse(
    val success: Boolean,
    val message: String,
    val token: String?
)