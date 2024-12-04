package com.bron24.bron24_android.domain.usecases.user

import com.bron24.bron24_android.domain.entity.user.User
import com.bron24.bron24_android.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPersonalUserDataUseCase @Inject constructor(
    private val userRepository:UserRepository
) {
    fun execute(): Flow<Result<User>> = userRepository.getPersonalUserData()

}