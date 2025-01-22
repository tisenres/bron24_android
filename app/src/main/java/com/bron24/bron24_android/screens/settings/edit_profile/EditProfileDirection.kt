package com.bron24.bron24_android.screens.settings.edit_profile

import com.bron24.bron24_android.navigator.AppNavigator
import com.bron24.bron24_android.screens.auth.phone_number.PhoneNumberScreen
import javax.inject.Inject

class EditProfileDirection @Inject constructor(
    private val appNavigator: AppNavigator
):EditProfileContract.Direction {
    override suspend fun back() {
        appNavigator.back()
    }

    override suspend fun moveToPhoneScreen() {
        appNavigator.replaceAll(PhoneNumberScreen())
    }
}