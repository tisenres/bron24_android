//package com.bron24.bron24_android.domain.usecases.auth
//
//import com.bron24.bron24_android.domain.entity.auth.AuthResponse
//import com.bron24.bron24_android.domain.entity.auth.OTPCodeResponse
//import com.bron24.bron24_android.domain.entity.auth.PhoneNumberResponse
//import com.bron24.bron24_android.domain.entity.user.User
//import javax.inject.Inject
//
//class AuthenticateFlowUseCase @Inject constructor(
//    private val requestOTPUseCase: RequestOTPUseCase,
//    private val verifyOTPUseCase: VerifyOTPUseCase,
//    private val authenticateUserUseCase: AuthenticateUserUseCase
//) {
//
//    suspend fun requestOTP(phoneNumber: String): PhoneNumberResponse {
//        return requestOTPUseCase.execute(phoneNumber)
//    }
//
//    suspend fun verifyOTP(phoneNumber: String, otp: Int): OTPCodeResponse {
//        return verifyOTPUseCase.execute(phoneNumber, otp)
//    }
//
//    suspend fun authenticateUser(user: User): AuthResponse {
//        return authenticateUserUseCase.execute(user)
//    }
//}
