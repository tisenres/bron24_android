package com.bron24.bron24_android.domain.entity.auth

import com.bron24.bron24_android.domain.entity.auth.enums.OTPStatusCode

data class OTPCodeResponse(
    val result: OTPStatusCode,
    val userExists: Boolean
)