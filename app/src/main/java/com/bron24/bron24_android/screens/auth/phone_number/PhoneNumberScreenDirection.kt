package com.bron24.bron24_android.screens.auth.phone_number

import android.util.Log
import com.bron24.bron24_android.navigator.AppNavigator
import com.bron24.bron24_android.screens.auth.sms_otp.OTPInputScreen
import javax.inject.Inject

class PhoneNumberScreenDirection @Inject constructor(
    private val appNavigator: AppNavigator
):PhoneNumberScreenContract.Direction {
    override suspend fun moveToNext(phoneNumber: String) {
        appNavigator.push(OTPInputScreen(phoneNumber))
    }
}