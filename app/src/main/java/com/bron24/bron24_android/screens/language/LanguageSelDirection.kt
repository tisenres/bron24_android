package com.bron24.bron24_android.screens.language

import com.bron24.bron24_android.navigator.AppNavigator
import com.bron24.bron24_android.screens.on_board.OnBoardPager
import javax.inject.Inject

class LanguageSelDirection @Inject constructor(
    private val appNavigator: AppNavigator
):LanguageSelContract.Direction {
    override suspend fun moveToNext() {
        appNavigator.replace(OnBoardPager())
    }
}