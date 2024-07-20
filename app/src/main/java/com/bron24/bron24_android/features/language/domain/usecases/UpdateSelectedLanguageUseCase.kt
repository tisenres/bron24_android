package com.bron24.bron24_android.features.language.domain.usecases

import com.bron24.bron24_android.features.language.domain.model.Language
import com.bron24.bron24_android.features.language.domain.repository.LanguageRepository
import javax.inject.Inject

class UpdateSelectedLanguageUseCase @Inject constructor(
    private val languageRepository: LanguageRepository
) {
    operator fun invoke(language: Language) {
        languageRepository.setSelectedLanguage(language)
    }
}
