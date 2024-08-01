package com.bron24.bron24_android.data.repository

import com.bron24.bron24_android.domain.repository.PreferencesRepository
import com.bron24.bron24_android.data.local.preference.AppPreference
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PreferencesRepositoryImpl @Inject constructor(
    private val appPreference: AppPreference
) : PreferencesRepository {

    override fun isOnboardingCompleted(): Boolean {
        return appPreference.isOnboardingCompleted()
    }

    override fun setOnboardingCompleted(completed: Boolean) {
        appPreference.setOnboardingCompleted(completed)
    }
}