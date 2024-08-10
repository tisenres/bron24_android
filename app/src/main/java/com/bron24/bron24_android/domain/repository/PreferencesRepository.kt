package com.bron24.bron24_android.domain.repository

interface PreferencesRepository {
    fun isOnboardingCompleted(): Boolean
    fun setOnboardingCompleted(completed: Boolean)
}