package com.bron24.bron24_android.domain.entity.auth.enums

enum class OTPStatusCode {
    CORRECT_OTP,
    INCORRECT_OTP,
    NETWORK_ERROR,
    UNKNOWN_ERROR,
    BANNED_USER
}