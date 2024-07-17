package com.bron24.bron24_android.features.language.data.repository

import com.bron24.bron24_android.features.language.data.local.LanguagePreference
import com.bron24.bron24_android.features.language.domain.model.Language
import javax.inject.Inject
import com.bron24.bron24_android.features.language.domain.repository.LanguageRepository

class LanguageRepositoryImpl @Inject constructor(
    private val languagePreference: LanguagePreference
) : LanguageRepository {
    override fun getAvailableLanguages(): List<Language> {
        return listOf(Language.UZBEK, Language.RUSSIAN)
    }

    override fun getSelectedLanguage(): Language {
        val languageCode = languagePreference.getSelectedLanguage() ?: "uz"
        return Language.entries.first { it.code == languageCode }
    }

    override fun setSelectedLanguage(language: Language) {
        languagePreference.setSelectedLanguage(language.code)
    }
}
