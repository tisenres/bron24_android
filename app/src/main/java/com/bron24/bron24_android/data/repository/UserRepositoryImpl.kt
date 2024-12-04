package com.bron24.bron24_android.data.repository

import com.bron24.bron24_android.data.local.preference.AppPreference
import com.bron24.bron24_android.domain.entity.user.User
import com.bron24.bron24_android.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val appPreference: AppPreference
) : UserRepository {
    override fun getPersonalUserData(): Flow<Result<User>> = flow {
       emit(Result.success(appPreference.getPersonalUserData()))
    }.catch { emit(Result.failure(it)) }.flowOn(Dispatchers.IO)
}