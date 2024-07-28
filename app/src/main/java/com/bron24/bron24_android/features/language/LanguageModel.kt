package com.bron24.bron24_android.features.language

import com.bron24.bron24_android.domain.usecases.language.GetAvailableLanguagesUseCase
import com.bron24.bron24_android.domain.entity.user.Language
import com.bron24.bron24_android.domain.usecases.language.SetUserLanguageUseCase
import javax.inject.Inject

class LanguageModel @Inject constructor(
    private val getAvailableLanguagesUseCase: GetAvailableLanguagesUseCase,
    private val setUserLanguageUseCase: SetUserLanguageUseCase
) {
    fun getAvailableLanguagesUseCase(): List<Language> {
        return getAvailableLanguagesUseCase.execute()
    }

    fun updateSelectedLanguageUseCase(language: Language) {
        setUserLanguageUseCase.execute(language)
    }
}