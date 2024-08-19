package com.bron24.bron24_android.data.network.mappers

import com.bron24.bron24_android.data.network.dto.auth.OTPCodeResponseDto
import com.bron24.bron24_android.data.network.dto.auth.PhoneNumberResponseDto
import com.bron24.bron24_android.data.network.dto.auth.AuthResponseDto
import com.bron24.bron24_android.data.network.dto.auth.LoginUserDto
import com.bron24.bron24_android.data.network.dto.auth.OTPRequestDto
import com.bron24.bron24_android.data.network.dto.auth.SignupUserDto
import com.bron24.bron24_android.domain.entity.auth.AuthResponse
import com.bron24.bron24_android.domain.entity.auth.OTPCodeResponse
import com.bron24.bron24_android.domain.entity.auth.OTPRequest
import com.bron24.bron24_android.domain.entity.auth.PhoneNumberResponse
import com.bron24.bron24_android.domain.entity.auth.enums.OTPStatusCode
import com.bron24.bron24_android.domain.entity.auth.enums.PhoneNumberResponseStatusCode
import com.bron24.bron24_android.domain.entity.user.User

fun OTPRequest.toNetworkModel(): OTPRequestDto {
    return OTPRequestDto(
        phoneNumber = this.phoneNumber,
        otp = this.otp
    )
}

fun PhoneNumberResponseDto.toDomainEntity(): PhoneNumberResponse {
    return PhoneNumberResponse(
        result = if (success) {
            PhoneNumberResponseStatusCode.SUCCESS
        } else {
            when (message) {
                "many requests" -> PhoneNumberResponseStatusCode.MANY_REQUESTS
                "Incorrect phone number format" -> PhoneNumberResponseStatusCode.INCORRECT_PHONE_NUMBER
                else -> PhoneNumberResponseStatusCode.NETWORK_ERROR
            }
        }
    )
}

fun OTPCodeResponseDto.toDomainEntity(): OTPCodeResponse {
    return OTPCodeResponse(
        result = if (success) {
            OTPStatusCode.CORRECT_OTP
        } else {
            OTPStatusCode.INCORRECT_OTP
        },
        userExists = data.userExists
    )
}

fun User.toLoginNetworkModel(): LoginUserDto {
    return LoginUserDto(
        phoneNumber = phoneNumber,
    )
}

fun User.toSignupNetworkModel(): SignupUserDto {
    return SignupUserDto(
        phoneNumber = phoneNumber,
        firstName = firstName,
        lastName = lastName
    )
}

fun AuthResponseDto.toDomainEntity(): AuthResponse {
    return AuthResponse(
        accessToken = data.accessToken,
        refreshToken = data.refreshToken,
    )
}