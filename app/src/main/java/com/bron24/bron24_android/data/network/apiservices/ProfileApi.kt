package com.bron24.bron24_android.data.network.apiservices

import com.bron24.bron24_android.data.network.dto.auth.DeleteAccountDto
import com.bron24.bron24_android.data.network.dto.auth.UpdateUserDto
import com.bron24.bron24_android.domain.entity.user.DeleteAcc
import com.bron24.bron24_android.domain.entity.user.UpdateProfile
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface ProfileApi {
    @PUT("api/v1/user/update/")
    suspend fun updateProfile(@Body updateProfile: UpdateProfile):UpdateUserDto

    @POST("api/v1/user/delete/")
    suspend fun deleteAccount(@Body deleteAcc: DeleteAcc):DeleteAccountDto
}