package com.bron24.bron24_android.data.local.preference

import android.content.Context
import android.content.SharedPreferences
import com.bron24.bron24_android.domain.entity.user.Language

class LanguagePreference(private val context: Context) {



    private val preferences: SharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    fun getSelectedLanguage(): String? {
        return preferences.getString("selected_language", null)
    }

    fun setSelectedLanguage(languageCode: String) {
        preferences.edit().putString("selected_language", languageCode).apply()
    }
}