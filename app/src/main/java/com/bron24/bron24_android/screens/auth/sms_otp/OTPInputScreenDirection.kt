package com.bron24.bron24_android.screens.auth.sms_otp

import com.bron24.bron24_android.domain.entity.user.LocationPermissionState
import com.bron24.bron24_android.domain.usecases.location.CheckLocationPermissionUseCase
import com.bron24.bron24_android.navigator.AppNavigator
import com.bron24.bron24_android.screens.auth.register.RegisterScreen
import com.bron24.bron24_android.screens.location.LocationScreen
import com.bron24.bron24_android.screens.menu_pages.screen_menu.MenuScreen
import javax.inject.Inject

class OTPInputScreenDirection @Inject constructor(
    private val checkLocationPermissionUseCase: CheckLocationPermissionUseCase,
    private val appNavigator: AppNavigator
):OTPInputContract.Direction {
    override suspend fun moveToNext() {
        checkLocationPermissionUseCase.invoke().collect{
            when(it){
                LocationPermissionState.GRANTED -> {
                    appNavigator.replaceAll(MenuScreen())
                }
                LocationPermissionState.DENIED -> {
                    appNavigator.replaceAll(LocationScreen())
                }
            }
        }
    }

    override suspend fun moveToRegister(phoneNumber:String) {
        appNavigator.push(RegisterScreen(phoneNumber))
    }

    override suspend fun back() {
        appNavigator.back()
    }
}