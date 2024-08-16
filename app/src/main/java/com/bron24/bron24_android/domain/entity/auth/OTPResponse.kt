package com.bron24.bron24_android.domain.entity.auth

import com.bron24.bron24_android.domain.entity.auth.enums.OTPStatusCode

data class OTPCodeResponse(
    val status: OTPStatusCode,
    val userExists: Boolean
)