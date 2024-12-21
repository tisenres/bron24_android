package com.bron24.bron24_android.screens.language

import com.bron24.bron24_android.navigator.AppNavigator
import javax.inject.Inject

class LanguageDirection @Inject constructor(
    private val appNavigator: AppNavigator
):LanguageContract.Direction {
    override suspend fun moveToNext() {
        //appNavigator.push()
    }
}