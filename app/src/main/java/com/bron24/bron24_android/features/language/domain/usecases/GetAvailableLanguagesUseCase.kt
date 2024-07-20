package com.bron24.bron24_android.features.language.domain.usecases

import com.bron24.bron24_android.features.language.domain.model.Language
import com.bron24.bron24_android.features.language.domain.repository.LanguageRepository
import javax.inject.Inject

class GetAvailableLanguagesUseCase @Inject constructor(
    private val languageRepository: LanguageRepository
) {
    operator fun invoke(): List<Language> {
        return languageRepository.getAvailableLanguages()
    }
}