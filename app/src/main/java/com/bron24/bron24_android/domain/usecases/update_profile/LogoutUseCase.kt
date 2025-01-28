package com.bron24.bron24_android.domain.usecases.update_profile

import com.bron24.bron24_android.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    operator fun invoke():Flow<Result<Unit>> = flow{
        emit(profileRepository.logoutProfile())
    }.catch { emit(Result.failure(it)) }
}