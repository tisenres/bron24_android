package com.bron24.bron24_android.data.network.mappers

import OTPCodeResponseDto
import PhoneNumberResponseDto
import com.bron24.bron24_android.data.network.dto.auth.AuthResponseDto
import com.bron24.bron24_android.data.network.dto.auth.OTPRequestDto
import com.bron24.bron24_android.data.network.dto.auth.UserDto
import com.bron24.bron24_android.domain.entity.auth.AuthResponse
import com.bron24.bron24_android.domain.entity.auth.OTPCodeResponse
import com.bron24.bron24_android.domain.entity.auth.OTPRequest
import com.bron24.bron24_android.domain.entity.auth.PhoneNumberResponse
import com.bron24.bron24_android.domain.entity.auth.enums.OTPStatusCode
import com.bron24.bron24_android.domain.entity.auth.enums.PhoneNumberResponseStatusCode
import com.bron24.bron24_android.domain.entity.user.User

fun OTPRequest.toNetworkModel(): OTPRequestDto {
    return OTPRequestDto(
        phone_number = this.phoneNumber,
        otp = this.otp
    )
}

fun PhoneNumberResponseDto.toDomainEntity(): PhoneNumberResponse {
    return PhoneNumberResponse(
        result = if (result == "success") {
            PhoneNumberResponseStatusCode.SUCCESS
        } else if (result == "many requests") {
            PhoneNumberResponseStatusCode.MANY_REQUESTS
        } else {
            PhoneNumberResponseStatusCode.INCORRECT_PHONE_NUMBER
        }
    )
}

fun OTPCodeResponseDto.toDomainEntity(): OTPCodeResponse {
    return OTPCodeResponse(
        status = if (status == "success") {
            OTPStatusCode.CORRECT_OTP
        } else {
            OTPStatusCode.INCORRECT_OTP
        }
    )
}

fun User.toNetworkModel(): UserDto {
    return UserDto(
        phoneNumber = phoneNumber,
        firstName = firstName,
        lastName = lastName
    )
}

fun AuthResponseDto.toDomainEntity(): AuthResponse {
    val accessExpiresAt = calculateExpirationTime(60) // 1 hour
    val refreshExpiresAt = calculateExpirationTime(90 * 24 * 60) // 90 days
    return AuthResponse(
        accessToken = this.accessToken,
        refreshToken = this.refreshToken,
        accessExpiresAt = accessExpiresAt,
        refreshExpiresAt = refreshExpiresAt
    )
}

private fun calculateExpirationTime(minutes: Long): Long {
    return System.currentTimeMillis() + minutes * 60 * 1000
}
