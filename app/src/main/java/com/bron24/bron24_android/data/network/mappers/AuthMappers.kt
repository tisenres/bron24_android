package com.bron24.bron24_android.data.network.mappers

import com.bron24.bron24_android.data.network.dto.auth.AuthResponseDto
import com.bron24.bron24_android.data.network.dto.auth.OTPCodeResponseDto
import com.bron24.bron24_android.data.network.dto.auth.OTPRequestDto
import com.bron24.bron24_android.data.network.dto.auth.PhoneNumberResponseDto
import com.bron24.bron24_android.data.network.dto.auth.UserDto
import com.bron24.bron24_android.domain.entity.auth.AuthResponse
import com.bron24.bron24_android.domain.entity.auth.OTPCodeResponse
import com.bron24.bron24_android.domain.entity.auth.OTPRequest
import com.bron24.bron24_android.domain.entity.auth.PhoneNumberResponse
import com.bron24.bron24_android.domain.entity.enums.OTPStatusCode
import com.bron24.bron24_android.domain.entity.user.User

fun OTPRequest.toNetworkModel(): OTPRequestDto {
    return OTPRequestDto(
        phone_number = this.phoneNumber,
        otp = this.otp
    )
}

fun PhoneNumberResponseDto.toDomainEntity(): PhoneNumberResponse {
    return PhoneNumberResponse(
        status = if (success.firstOrNull() == "success") OTPStatusCode.SUCCESS else OTPStatusCode.ERROR,
    )
}

fun OTPCodeResponseDto.toDomainEntity(): OTPCodeResponse {
    return OTPCodeResponse(
        status = if (status == "success") OTPStatusCode.SUCCESS else OTPStatusCode.ERROR,
    )
}

fun User.toNetworkModel(): UserDto {
    return UserDto(
        phoneNumber = phoneNumber,
        firstName = firstName,
        secondName = secondName
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
