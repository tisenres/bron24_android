package com.bron24.bron24_android.di

import com.bron24.bron24_android.domain.usecases.language.GetAvailableLanguagesUseCase
import com.bron24.bron24_android.domain.usecases.language.SetUserLanguageUseCase
import com.bron24.bron24_android.features.language.LanguageModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModelModule {

    @Provides
    @Singleton
    fun provideLanguageModel(
        getAvailableLanguagesUseCase: GetAvailableLanguagesUseCase,
        setUserLanguageUseCase: SetUserLanguageUseCase
    ): LanguageModel {
        return LanguageModel(
            getAvailableLanguagesUseCase,
            setUserLanguageUseCase
        )
    }
}