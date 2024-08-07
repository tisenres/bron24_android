package com.bron24.bron24_android.data.network.dto.auth

data class OTPResponse(
    val status: String,
    val access: String? = null,
    val refresh: String? = null,
    val success: Boolean? = null
)