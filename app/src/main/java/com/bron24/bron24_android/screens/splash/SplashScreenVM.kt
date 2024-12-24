package com.bron24.bron24_android.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bron24.bron24_android.common.ScreenState
import com.bron24.bron24_android.data.local.preference.LocalStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject
@HiltViewModel
class SplashScreenVM @Inject constructor(
    private val localStorage: LocalStorage,
    private val direction: SplashScreenContract.Direction
): ViewModel(), SplashScreenContract.ViewModel{
    init {
        viewModelScope.launch {
            if(localStorage.openMenu){
                delay(1000)
                direction.moveToNext(ScreenState.MENU)
            }else if(localStorage.openPhoneNumber){
                delay(1000)
                direction.moveToNext(ScreenState.PHONE)
            }else if(localStorage.openOnBoard){
                delay(1000)
                direction.moveToNext(ScreenState.ON_BOARD)
            }else{
                delay(1000)
                direction.moveToNext(ScreenState.LANGUAGE)
            }
        }
    }
    override val container = container<SplashScreenContract.UIState, SplashScreenContract.SideEffect>(SplashScreenContract.UIState())
}