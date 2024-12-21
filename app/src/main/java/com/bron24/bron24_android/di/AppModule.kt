package com.bron24.bron24_android.di

import android.content.Context
import com.bron24.bron24_android.data.local.preference.AppPreference
import com.bron24.bron24_android.domain.usecases.language.GetSelectedLanguageUseCase
import com.bron24.bron24_android.helper.util.LocaleManager
import com.bron24.bron24_android.helper.util.PermissionChecker
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
    fun provideAppPreference(@ApplicationContext context: Context): AppPreference {
        return AppPreference(context)
    }

    @Provides
    @Singleton
    fun providePermissionChecker(@ApplicationContext context: Context): PermissionChecker {
        return PermissionChecker(context)
    }

//    @Provides
//    @Singleton
//    fun provideLocaleManager(
//        getSelectedLanguageUseCase: GetSelectedLanguageUseCase
//    ): LocaleManager {
//        return LocaleManager(getSelectedLanguageUseCase)
//    }
}