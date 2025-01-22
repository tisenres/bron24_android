package com.bron24.bron24_android.domain.usecases.auth

import com.bron24.bron24_android.domain.entity.auth.OTPRequest
import com.bron24.bron24_android.domain.entity.auth.PhoneNumberResponse
import com.bron24.bron24_android.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RequestOTPUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(phoneNumber: String): Flow<Result<PhoneNumberResponse>> = flow {
        val request = OTPRequest(phoneNumber.substring(1))
        emit(Result.success(authRepository.requestOTP(request)))
    }.catch { emit(Result.failure(it)) }.flowOn(Dispatchers.IO)
}
