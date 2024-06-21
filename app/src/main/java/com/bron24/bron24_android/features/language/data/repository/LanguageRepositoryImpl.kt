package com.bron24.bron24_android.features.language.data.repository

import com.bron24.bron24_android.features.language.data.local.LanguagePreference
import com.bron24.bron24_android.features.language.domain.model.Language
import javax.inject.Inject

class LanguageRepositoryImpl @Inject constructor(
    private val languagePreference: LanguagePreference
) {
    fun getAvailableLanguages(): List<Language> {
        return listOf(Language.UZBEK, Language.RUSSIAN, Language.ENGLISH)
    }

    fun getSelectedLanguage(): Language {
        val languageCode = languagePreference.getSelectedLanguage() ?: "uz"
        return Language.entries.first { it.code == languageCode }
    }

    fun setSelectedLanguage(language: Language) {
        languagePreference.setSelectedLanguage(language.code)
    }
}