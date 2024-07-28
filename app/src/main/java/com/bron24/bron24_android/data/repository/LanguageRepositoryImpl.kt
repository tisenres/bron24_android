package com.bron24.bron24_android.data.repository

import com.bron24.bron24_android.data.local.preference.LanguagePreference
import com.bron24.bron24_android.domain.entity.user.Language
import com.bron24.bron24_android.domain.repository.LanguageRepository
import javax.inject.Inject

class LanguageRepositoryImpl @Inject constructor(
    private val languagePreference: LanguagePreference
) : LanguageRepository {
    override fun getAvailableLanguages(): List<Language> {
        return listOf(Language("uz", "wewewe"))
    }

    override fun getSelectedLanguage(): Language {
        val languageCode = languagePreference.getSelectedLanguage() ?: Language("uz", "wewewe").languageCode
        return listOf(Language("uz", "wewewe")).first { it.languageCode == languageCode }
    }

    override fun setSelectedLanguage(language: Language) {
        languagePreference.setSelectedLanguage(language.languageCode)
    }
}