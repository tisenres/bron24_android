package com.bron24.bron24_android.domain.usecases.language

import com.bron24.bron24_android.domain.entity.user.Language
import com.bron24.bron24_android.domain.repository.LanguageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SetUserLanguageUseCase @Inject constructor(private val languageRepository: LanguageRepository) {
  operator fun invoke(language: Language): Flow<Result<Unit>> = languageRepository.setSelectedLanguage(language)
}