package com.bron24.bron24_android.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber: StateFlow<String> = _phoneNumber

    init {
        viewModelScope.launch {
            onboardingUseCase.isOnboardingCompleted().collect { completed ->
                _isOnboardingCompleted.value = completed
            }
        }
    }

    fun updatePhoneNumber(phone: String) {
        _phoneNumber.value = phone
    }

    fun setOnboardingCompleted() {
        viewModelScope.launch {
            onboardingUseCase.setOnboardingCompleted(true)
        }
    }
}