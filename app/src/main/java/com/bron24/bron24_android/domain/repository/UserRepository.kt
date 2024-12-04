package com.bron24.bron24_android.domain.repository

import com.bron24.bron24_android.domain.entity.user.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getPersonalUserData(): Flow<Result<User>>
}