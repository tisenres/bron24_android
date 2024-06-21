package com.bron24.bron24_android.features.language.domain.usecases

import com.bron24.bron24_android.features.language.data.repository.LanguageRepositoryImpl
import com.bron24.bron24_android.features.language.domain.model.Language
import javax.inject.Inject

class UpdateSelectedLanguageUseCase @Inject constructor(
    private val languageRepository: LanguageRepositoryImpl
) {
    fun execute(language: Language) {
        languageRepository.setSelectedLanguage(language)
    }
}