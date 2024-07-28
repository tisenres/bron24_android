package com.bron24.bron24_android.data.repository

import com.bron24.bron24_android.data.local.preference.LanguagePreference
import com.bron24.bron24_android.domain.entity.user.Language
import com.bron24.bron24_android.domain.repository.LanguageRepository
import javax.inject.Inject

class LanguageRepositoryImpl @Inject constructor(
    private val languagePreference: LanguagePreference
) : LanguageRepository {

    private val availableLanguages = listOf(
        Language("uz", "O`zbek"),
        Language("ru", "Russian"),
        Language("en", "English")
        )

    override fun getAvailableLanguages(): List<Language> {
        return availableLanguages
    }

    override fun getSelectedLanguage(): Language {
        val languageCode = languagePreference.getSelectedLanguage() ?: Language("uz", "wewewe").languageCode
        return availableLanguages.first { it.languageCode == languageCode }
    }

    override fun setSelectedLanguage(language: Language) {
        languagePreference.setSelectedLanguage(language.languageCode)
    }
}