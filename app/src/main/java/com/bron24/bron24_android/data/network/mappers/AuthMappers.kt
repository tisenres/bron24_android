package com.bron24.bron24_android.data.network.mappers

import com.bron24.bron24_android.data.network.dto.auth.OTPRequest
import com.bron24.bron24_android.data.network.dto.auth.OTPResponse
import com.bron24.bron24_android.data.network.dto.auth.toRequestErrorList
import com.bron24.bron24_android.domain.entity.auth.OTPRequestEntity
import com.bron24.bron24_android.domain.entity.auth.OTPResponseEntity

fun OTPRequestEntity.toNetworkModel(): OTPRequest {
    return OTPRequest(
        phone_number = this.phoneNumber,
        otp = this.otp
    )
}

fun OTPResponse.toDomainEntity(): OTPResponseEntity {
    val requestErrorList = this.toRequestErrorList()
    val messageId = requestErrorList.firstOrNull()?.requestError?.serviceException?.messageId ?: "UNKNOWN"
    val text = requestErrorList.firstOrNull()?.requestError?.serviceException?.text ?: "Unknown error"

    return OTPResponseEntity(
        status = messageId,
        access = null, // Map appropriately if you have this field in your response
        refresh = null, // Map appropriately if you have this field in your response
        success = text != "Invalid login details" // Assuming success is based on error text
    )
}
