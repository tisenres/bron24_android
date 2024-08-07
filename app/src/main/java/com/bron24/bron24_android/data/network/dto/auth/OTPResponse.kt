package com.bron24.bron24_android.data.network.dto.auth

import com.google.gson.annotations.SerializedName

data class PhoneNumberResponse(
    @SerializedName("success") val success: List<String>
)

data class OTPCodeResponse(
    @SerializedName("status") val status: String
)

//
//data class RequestError(
//    val requestError: ServiceException
//)
//
//data class ServiceException(
//    val serviceException: MessageException
//)
//
//data class MessageException(
//    val messageId: String,
//    val text: String
//)
//
//fun String.toRequestError(): RequestError {
//    return Gson().fromJson(this, RequestError::class.java)
//}
//
//fun OTPResponse.toRequestErrorList(): List<RequestError> {
//    return this.success.map { it.toRequestError() }
//}