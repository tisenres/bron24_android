package com.bron24.bron24_android.features.language.domain.usecases

import com.bron24.bron24_android.features.language.data.repository.LanguageRepositoryImpl
import com.bron24.bron24_android.features.language.domain.model.Language
import javax.inject.Inject

class GetAvailableLanguagesUseCase @Inject constructor(
    private val languageRepository: LanguageRepositoryImpl
) {
    fun execute(): List<Language> {
        return languageRepository.getAvailableLanguages()
    }
}