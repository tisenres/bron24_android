//package com.bron24.bron24_android.features.language.data.repository
//
//import com.bron24.bron24_android.features.language.data.local.LanguagePreference
//import com.bron24.bron24_android.features.language.domain.entities.Language
//import com.bron24.bron24_android.features.language.domain.repository.LanguageRepository
//import javax.inject.Inject
//
//class LanguageRepositoryImpl @Inject constructor(
//    private val languagePreference: LanguagePreference
//) : LanguageRepository {
//    override fun getAvailableLanguages(): List<Language> {
//        return Language.values()
//    }
//
//    override fun getSelectedLanguage(): Language {
//        val languageCode = languagePreference.getSelectedLanguage() ?: Language.UZBEK().code
//        return Language.values().first { it.code == languageCode }
//    }
//
//    override fun setSelectedLanguage(language: Language) {
//        languagePreference.setSelectedLanguage(language.code)
//    }
//}