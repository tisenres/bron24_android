package com.bron24.bron24_android.domain.usecases.auth

import coil.size.Dimension
import com.bron24.bron24_android.domain.entity.auth.OTPCodeResponse
import com.bron24.bron24_android.domain.entity.auth.OTPRequest
import com.bron24.bron24_android.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class VerifyOTPUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(phoneNumber: String, otp: Int): Flow<Result<OTPCodeResponse>> = flow {
        val request = OTPRequest(phoneNumber, otp)
        emit(Result.success(authRepository.verifyOTP(request)))
    }.catch { emit(Result.failure(it)) }.flowOn(Dispatchers.IO)
}