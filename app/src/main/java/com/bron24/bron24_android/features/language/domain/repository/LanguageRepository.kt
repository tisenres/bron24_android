package com.bron24.bron24_android.features.language.domain.repository

import com.bron24.bron24_android.features.language.domain.model.Language

interface LanguageRepository {
    fun getAvailableLanguages(): List<Language>
    fun getSelectedLanguage(): Language
    fun setSelectedLanguage(language: Language)
}