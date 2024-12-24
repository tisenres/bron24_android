package com.bron24.bron24_android.screens.auth

import com.bron24.bron24_android.domain.entity.auth.AuthResponse
import com.bron24.bron24_android.domain.entity.auth.OTPCodeResponse
import com.bron24.bron24_android.domain.entity.auth.PhoneNumberResponse
import com.bron24.bron24_android.domain.entity.user.User
import com.bron24.bron24_android.domain.usecases.auth.*
import javax.inject.Inject

class AuthModel @Inject constructor(
    private val requestOTPUseCase: RequestOTPUseCase,
    private val verifyOTPUseCase: VerifyOTPUseCase,
    private val authenticateUserUseCase: AuthenticateUserUseCase
) {

    private var userExists: Boolean = false


//    suspend fun verifyOTP(phoneNumber: String, otp: Int): OTPCodeResponse {
//        val response = verifyOTPUseCase.execute(phoneNumber, otp)
//        userExists = response.userExists
//        if (userExists) {
//            authenticateUserUseCase.execute(
//                User(
//                    firstName = "",
//                    lastName = "",
//                    phoneNumber = phoneNumber,
//                ),
//                userExists
//            )
//        }
//        return response
//    }

//    suspend fun authUser(phoneNumber: String, firstName: String, lastName: String): AuthResponse {
//        return authenticateUserUseCase.(
//            User(
//                firstName = firstName,
//                lastName = lastName,
//                phoneNumber = phoneNumber,
//            ),
//            userExists
//        )
//    }
}