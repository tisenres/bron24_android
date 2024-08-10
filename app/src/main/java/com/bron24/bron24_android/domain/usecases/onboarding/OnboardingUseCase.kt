package com.bron24.bron24_android.domain.usecases.onboarding

import com.bron24.bron24_android.domain.entity.enums.OnboardingScreen
import com.bron24.bron24_android.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OnboardingUseCase @Inject constructor(
    private val repository: PreferencesRepository
) {
    fun isOnboardingCompleted(screenName: OnboardingScreen): Flow<Boolean> = flow {
        emit(repository.isOnboardingCompleted(screenName))
    }

//    fun setOnboardingCompleted(completed: Boolean) {
//        repository.setOnboardingCompleted(completed)
//    }

    fun setOnboardingCompleted(screen: OnboardingScreen, completed: Boolean) {
        repository.setOnboardingCompleted(screen, completed)
    }
}