package com.bron24.bron24_android.domain.repository

import com.bron24.bron24_android.domain.entity.user.Language
import kotlinx.coroutines.flow.Flow

interface LanguageRepository {
    fun getAvailableLanguages(): Flow<Result<List<Language>>>
    fun getSelectedLanguage(): Language
    fun setSelectedLanguage(language: Language):Flow<Result<Unit>>
}