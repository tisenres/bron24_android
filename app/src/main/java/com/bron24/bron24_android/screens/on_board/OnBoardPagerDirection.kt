package com.bron24.bron24_android.screens.on_board

import com.bron24.bron24_android.navigator.AppNavigator
import com.bron24.bron24_android.screens.auth.phone_number.PhoneNumberScreen
import javax.inject.Inject

class OnBoardPagerDirection @Inject constructor(
    private val appNavigator: AppNavigator
):OnBoardPagerContract.Direction {
    override suspend fun moveToNext() {
        appNavigator.replace(PhoneNumberScreen())
    }
}