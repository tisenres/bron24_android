package com.bron24.bron24_android.domain.repository

import com.bron24.bron24_android.domain.entity.user.User

interface UserRepository {
    fun getPersonalUserData(): User
}