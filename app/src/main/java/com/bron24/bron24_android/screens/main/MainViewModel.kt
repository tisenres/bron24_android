package com.bron24.bron24_android.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bron24.bron24_android.domain.entity.enums.OnboardingScreen
import com.bron24.bron24_android.domain.usecases.onboarding.OnboardingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val onboardingUseCase: OnboardingUseCase
) : ViewModel() {

    private val _isOnboardingCompleted = MutableStateFlow(false)
    val isOnboardingCompleted: StateFlow<Boolean> = _isOnboardingCompleted

    private val _onboardingScreensToShow = MutableStateFlow<List<Screen>>(emptyList())
    val onboardingScreensToShow: StateFlow<List<Screen>> = _onboardingScreensToShow

    init {
        viewModelScope.launch {
            val screensToShow = mutableListOf<Screen>()
            for (screen in OnboardingScreen.entries) {
                onboardingUseCase.isOnboardingCompleted(screen).collect { completed ->
                    if (!completed) {
                        screensToShow.add(mapOnboardingScreenToRoute(screen))
                    }
                }
            }
            _onboardingScreensToShow.value = screensToShow
            _isOnboardingCompleted.value = screensToShow.isEmpty()
        }
    }

    fun setOnboardingCompleted(screen: OnboardingScreen) {
        viewModelScope.launch {
            onboardingUseCase.setOnboardingCompleted(screen, true)
            val route = mapOnboardingScreenToRoute(screen)
            _onboardingScreensToShow.value = _onboardingScreensToShow.value.filter { it != route }
            _isOnboardingCompleted.value = _onboardingScreensToShow.value.isEmpty()
        }
    }

    private fun mapOnboardingScreenToRoute(screen: OnboardingScreen): Screen {
        return when (screen) {
            OnboardingScreen.LANGUAGE -> Screen.LanguageSelection
            OnboardingScreen.HOWITWORKS -> Screen.HowItWorksPager
            OnboardingScreen.AUTHENTICATION -> Screen.PhoneNumberInput
            OnboardingScreen.LOCATION -> Screen.LocationPermission
        }
    }
}

//@HiltViewModel
//class MainViewModel @Inject constructor(
//    private val onboardingUseCase: OnboardingUseCase
//) : ViewModel() {
//
//    private val _isOnboardingCompleted = MutableStateFlow(false)
//    val isOnboardingCompleted: StateFlow<Boolean> = _isOnboardingCompleted

//    private val _phoneNumber = MutableStateFlow("")
//    val phoneNumber: StateFlow<String> = _phoneNumber

//    init {
//        viewModelScope.launch {
//            onboardingUseCase.isOnboardingCompleted().collect { completed ->
//                _isOnboardingCompleted.value = completed
//            }
//        }
//    }

//    fun isOnboardingCompleted(screen: OnboardingScreen) {
//        viewModelScope.launch {
//            onboardingUseCase.isOnboardingCompleted(screen).collect { completed ->
//                _isOnboardingCompleted.value = completed
//            }
//        }
//    }

//    fun updatePhoneNumber(phone: String) {
//        _phoneNumber.value = phone
//    }

//    fun setOnboardingCompleted() {
//        viewModelScope.launch {
//            onboardingUseCase.setOnboardingCompleted(true)
//        }
//    }

//    fun setOnboardingCompleted(screen: OnboardingScreen) {
//        viewModelScope.launch {
//            onboardingUseCase.setOnboardingCompleted(screen, true)
//        }
//    }
//}