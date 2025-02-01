package com.bron24.bron24_android.data.repository

import androidx.compose.ui.input.key.Key.Companion.U
import com.bron24.bron24_android.data.local.preference.LocalStorage
import com.bron24.bron24_android.data.network.apiservices.ProfileApi
import com.bron24.bron24_android.domain.entity.user.DeleteAcc
import com.bron24.bron24_android.domain.entity.user.UpdateProfile
import com.bron24.bron24_android.domain.repository.ProfileRepository
import com.bron24.bron24_android.domain.repository.TokenRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val localStorage: LocalStorage,
    private val profileApi: ProfileApi
) :ProfileRepository {
    override suspend fun updateProfile(updateProfile: UpdateProfile): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val result = profileApi.updateProfile(updateProfile)
            if (result.success) {
                localStorage.firstName = result.data.firstName
                localStorage.lastName = result.data.lastName
                Result.success(Unit)
            } else Result.failure(Exception(result.message))
        }catch (e:Exception){
            Result.failure(e)
        }
    }

    override suspend fun deleteProfile(type: String?): Result<String> = withContext(Dispatchers.IO) {
        try {
            val result = profileApi.deleteAccount(DeleteAcc(type?:"hard"))
            if(result.success){
                localStorage.openMenu = false
                localStorage.firstName =""
                localStorage.lastName = ""
                localStorage.phoneNumber = ""
                Result.success(result.message)
            }else{
                Result.failure(Exception("Unknown error!"))
            }
        }catch (e:Exception){
            Result.failure(e)
        }
    }

    override suspend fun logoutProfile(): Result<Unit>  {
        localStorage.openMenu = false
        localStorage.firstName =""
        localStorage.lastName = ""
        localStorage.phoneNumber = ""
        return Result.success(Unit)
    }
}