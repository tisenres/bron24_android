package com.bron24.bron24_android.domain.usecases.language

import com.bron24.bron24_android.features.language.domain.entities.Language
import com.bron24.bron24_android.features.language.domain.repository.LanguageRepository

class GetAvailableLanguagesUseCase(
    private val languageRepository: LanguageRepository
) {
    fun execute(): List<Language> {
        return languageRepository.getAvailableLanguages()
    }
}