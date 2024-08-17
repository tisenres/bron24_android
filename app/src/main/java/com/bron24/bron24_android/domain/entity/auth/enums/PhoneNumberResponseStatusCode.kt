package com.bron24.bron24_android.domain.entity.auth.enums

enum class PhoneNumberResponseStatusCode {
    SUCCESS,
    MANY_REQUESTS,
    INCORRECT_PHONE_NUMBER,
    NETWORK_ERROR,
    UNKNOWN_ERROR,
}