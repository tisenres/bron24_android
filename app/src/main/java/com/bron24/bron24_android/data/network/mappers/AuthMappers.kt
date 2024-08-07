package com.bron24.bron24_android.data.network.mappers

import com.bron24.bron24_android.data.network.dto.auth.OTPCodeResponse
import com.bron24.bron24_android.data.network.dto.auth.OTPRequest
import com.bron24.bron24_android.data.network.dto.auth.PhoneNumberResponse
import com.bron24.bron24_android.domain.entity.auth.OTPCodeResponseEntity
import com.bron24.bron24_android.domain.entity.auth.OTPRequestEntity
import com.bron24.bron24_android.domain.entity.auth.PhoneNumberResponseEntity
import com.bron24.bron24_android.domain.entity.enums.OTPStatusCode

fun OTPRequestEntity.toNetworkModel(): OTPRequest {
    return OTPRequest(
        phone_number = this.phoneNumber,
        otp = this.otp
    )
}

fun PhoneNumberResponse.toDomainEntity(): PhoneNumberResponseEntity {
    return PhoneNumberResponseEntity(
        status = if (success.firstOrNull() == "success") OTPStatusCode.SUCCESS else OTPStatusCode.ERROR,
    )
}

fun OTPCodeResponse.toDomainEntity(): OTPCodeResponseEntity {
    return OTPCodeResponseEntity(
        status = if (status == "success") OTPStatusCode.SUCCESS else OTPStatusCode.ERROR,
    )
}
