package com.bron24.bron24_android.data.network.mappers

import com.bron24.bron24_android.data.network.dto.auth.OTPRequest
import com.bron24.bron24_android.data.network.dto.auth.OTPResponse
import com.bron24.bron24_android.domain.entity.auth.OTPRequestEntity
import com.bron24.bron24_android.domain.entity.auth.OTPResponseEntity

fun OTPRequestEntity.toNetworkModel(): OTPRequest {
    return OTPRequest(
        phone_number = this.phoneNumber,
        otp = this.otp
    )
}

fun OTPResponse.toDomainEntity(): OTPResponseEntity {
    return OTPResponseEntity(
        status = this.status,
        access = this.access,
        refresh = this.refresh,
        success = this.success
    )
}
