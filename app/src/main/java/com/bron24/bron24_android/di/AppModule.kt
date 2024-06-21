package com.bron24.bron24_android.di

import android.content.Context
import com.bron24.bron24_android.features.language.data.local.LanguagePreference
import com.bron24.bron24_android.features.language.data.repository.LanguageRepositoryImpl
import com.bron24.bron24_android.features.language.domain.usecases.GetAvailableLanguagesUseCase
import com.bron24.bron24_android.features.language.domain.usecases.UpdateSelectedLanguageUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLanguagePreference(@ApplicationContext context: Context): LanguagePreference {
        return LanguagePreference(context)
    }

    @Provides
    @Singleton
    fun provideLanguageRepository(
        languagePreference: LanguagePreference
    ): LanguageRepositoryImpl {
        return LanguageRepositoryImpl(languagePreference)
    }

    @Provides
    @Singleton
    fun provideGetAvailableLanguagesUseCase(
        languageRepository: LanguageRepositoryImpl
    ): GetAvailableLanguagesUseCase {
        return GetAvailableLanguagesUseCase(languageRepository)
    }

    @Provides
    @Singleton
    fun provideUpdateSelectedLanguageUseCase(
        languageRepository: LanguageRepositoryImpl
    ): UpdateSelectedLanguageUseCase {
        return UpdateSelectedLanguageUseCase(languageRepository)
    }
}
