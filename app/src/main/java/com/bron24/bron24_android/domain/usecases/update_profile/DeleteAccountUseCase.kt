package com.bron24.bron24_android.domain.usecases.update_profile

import com.bron24.bron24_android.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteAccountUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    operator fun invoke():Flow<String> = flow{
        profileRepository.deleteProfile("hard").onSuccess {
            emit(it)
        }.onFailure { emit(it.message?:"Unknown error!") }
    }.catch { emit(it.message?:"Unknown error!") }
}