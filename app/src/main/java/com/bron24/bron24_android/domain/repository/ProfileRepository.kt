package com.bron24.bron24_android.domain.repository

import com.bron24.bron24_android.domain.entity.user.UpdateProfile

interface ProfileRepository {
    suspend fun updateProfile(updateProfile: UpdateProfile):Result<Unit>
    suspend fun deleteProfile(type:String? = null):Result<String>
}