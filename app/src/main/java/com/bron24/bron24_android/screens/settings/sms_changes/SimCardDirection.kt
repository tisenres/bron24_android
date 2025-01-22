package com.bron24.bron24_android.screens.settings.sms_changes

import com.bron24.bron24_android.navigator.AppNavigator
import javax.inject.Inject

class SimCardDirection @Inject constructor(
    private val appNavigator: AppNavigator
):SimCardContract.Direction {
    override suspend fun back() {
        appNavigator.back()
    }

    override suspend fun moveToNext(phone: String) {

    }
}