package com.bron24.bron24_android.data.repository

import com.bron24.bron24_android.data.local.preference.AppPreference
import com.bron24.bron24_android.domain.entity.user.User
import com.bron24.bron24_android.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val appPreference: AppPreference
) : UserRepository {
    override fun getPersonalUserData(): User {
        return appPreference.getPersonalUserData()
    }
}