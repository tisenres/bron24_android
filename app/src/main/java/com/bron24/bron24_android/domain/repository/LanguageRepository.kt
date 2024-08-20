package com.bron24.bron24_android.domain.repository

import com.bron24.bron24_android.domain.entity.user.Language

interface LanguageRepository {
    fun getAvailableLanguages(): List<Language>
    fun getSelectedLanguage(): Language
    fun setSelectedLanguage(language: Language)
}