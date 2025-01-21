package com.bron24.bron24_android.domain.usecases.update_profile

import com.bron24.bron24_android.domain.entity.user.UpdateProfile
import com.bron24.bron24_android.domain.repository.AuthRepository
import com.bron24.bron24_android.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    operator fun invoke(updateProfile: UpdateProfile): Flow<Result<Unit>> = flow<Result<Unit>> {
        profileRepository.updateProfile(updateProfile)
            .onSuccess {
                emit(Result.success(Unit))
            }.onFailure {
                emit(Result.failure(it))
            }
    }.catch { emit(Result.failure(it)) }
}