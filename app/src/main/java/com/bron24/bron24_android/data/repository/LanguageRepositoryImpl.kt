package com.bron24.bron24_android.data.repository

import com.bron24.bron24_android.data.local.preference.AppPreference
import com.bron24.bron24_android.domain.entity.user.Language
import com.bron24.bron24_android.domain.repository.LanguageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LanguageRepositoryImpl @Inject constructor(
    private val appPreference: AppPreference
) : LanguageRepository {

    private val availableLanguages = listOf(
        Language("uz", "O`zbek"),
        Language("ru", "Russian"),
        Language("en", "English")
    )

    override fun getAvailableLanguages(): Flow<Result<List<Language>>> = flow {
        emit(Result.success(availableLanguages))
    }.catch { emit(Result.failure(it)) }.flowOn(Dispatchers.IO)

    override fun getSelectedLanguage(): Language {
        val languageCode = appPreference.getSelectedLanguage() ?: Language("uz", "O`zbek'").languageCode
        return availableLanguages.first { it.languageCode == languageCode }

    }

    override fun setSelectedLanguage(language: Language): Flow<Result<Unit>> = flow{
        appPreference.setSelectedLanguage(language.languageCode)
        emit(Result.success(Unit))
    }.catch { emit(Result.failure(it)) }.flowOn(Dispatchers.IO)
}