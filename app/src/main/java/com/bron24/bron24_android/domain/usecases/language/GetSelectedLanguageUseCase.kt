package com.bron24.bron24_android.domain.usecases.language

import com.bron24.bron24_android.domain.entity.user.Language
import com.bron24.bron24_android.domain.repository.LanguageRepository
import javax.inject.Inject

class GetSelectedLanguageUseCase @Inject constructor(
    private val languageRepository: LanguageRepository
) {
    fun execute(): Language {
        return languageRepository.getSelectedLanguage()
    }
}