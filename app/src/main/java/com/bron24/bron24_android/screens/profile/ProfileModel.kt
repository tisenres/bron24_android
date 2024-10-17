package com.bron24.bron24_android.screens.profile

import com.bron24.bron24_android.domain.entity.user.User
import com.bron24.bron24_android.domain.usecases.user.GetPersonalUserDataUseCase
import javax.inject.Inject

class ProfileModel @Inject constructor(
    private val getPersonalUserDataUseCase: GetPersonalUserDataUseCase
) {

    fun getPersonalUserData(): User {
        return getPersonalUserDataUseCase.execute()
    }

}