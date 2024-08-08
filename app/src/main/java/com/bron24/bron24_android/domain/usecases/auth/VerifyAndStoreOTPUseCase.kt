//package com.bron24.bron24_android.domain.usecases.auth
//
//import com.bron24.bron24_android.domain.entity.auth.OTPCodeResponseEntity
//import com.bron24.bron24_android.domain.entity.enums.OTPStatusCode
//import javax.inject.Inject
//
//class VerifyAndStoreOTPUseCase @Inject constructor(
//    private val verifyOTPUseCase: VerifyOTPUseCase,
//    private val saveTokenUseCase: SaveTokenUseCase
//) {
//    suspend fun execute(phoneNumber: String, otp: Int): OTPCodeResponseEntity {
//        val response = verifyOTPUseCase.execute(phoneNumber, otp)
//        if (response.status == OTPStatusCode.SUCCESS) {
////            val expiryTime = System.currentTimeMillis() + 90L * 24 * 60 * 60 * 1000 // 90 days in milliseconds
////            saveTokenUseCase.execute(response.s ?: "", expiryTime)
//        }
//        return response
//    }
//}
