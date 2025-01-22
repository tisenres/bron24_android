package com.bron24.bron24_android.screens.auth.sms_otp

import com.bron24.bron24_android.navigator.AppNavigator
import com.bron24.bron24_android.screens.auth.register.RegisterScreen
import com.bron24.bron24_android.screens.location.LocationScreen
import com.bron24.bron24_android.screens.menu_pages.screen_menu.MenuScreen
import javax.inject.Inject

class OTPInputScreenDirection @Inject constructor(
    private val appNavigator: AppNavigator
):OTPInputContract.Direction {
    override suspend fun moveToMenu() {
        appNavigator.replaceAll(LocationScreen())
    }

    override suspend fun moveToRegister(phoneNumber:String) {
        appNavigator.push(RegisterScreen(phoneNumber))
    }

    override suspend fun back() {
        appNavigator.back()
    }
}