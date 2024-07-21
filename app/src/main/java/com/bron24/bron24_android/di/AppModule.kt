package com.bron24.bron24_android.di

import android.content.Context
import com.bron24.bron24_android.features.cityselection.data.CityRepositoryImpl
import com.bron24.bron24_android.features.cityselection.domain.repository.CityRepository
import com.bron24.bron24_android.features.cityselection.domain.usecases.GetAvailableCitiesUseCase
import com.bron24.bron24_android.features.cityselection.domain.usecases.UpdateSelectedCityUseCase
import com.bron24.bron24_android.features.language.data.local.LanguagePreference
import com.bron24.bron24_android.features.language.data.repository.LanguageRepositoryImpl
import com.bron24.bron24_android.features.language.domain.repository.LanguageRepository
import com.bron24.bron24_android.features.language.domain.usecases.GetAvailableLanguagesUseCase
import com.bron24.bron24_android.features.language.domain.usecases.UpdateSelectedLanguageUseCase
import com.bron24.bron24_android.location.data.local.PermissionChecker
import com.bron24.bron24_android.location.domain.repository.LocationRepository
import com.bron24.bron24_android.location.domain.usecases.CheckLocationPermissionUseCase
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
    fun provideGetAvailableLanguagesUseCase(
        languageRepository: LanguageRepository
    ): GetAvailableLanguagesUseCase {
        return GetAvailableLanguagesUseCase(languageRepository)
    }

    @Provides
    @Singleton
    fun provideUpdateSelectedLanguageUseCase(
        languageRepository: LanguageRepository
    ): UpdateSelectedLanguageUseCase {
        return UpdateSelectedLanguageUseCase(languageRepository)
    }

    @Provides
    @Singleton
    fun provideCityRepository(@ApplicationContext context: Context): CityRepository {
        return CityRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun provideGetAvailableCitiesUseCase(
        cityRepository: CityRepository
    ): GetAvailableCitiesUseCase {
        return GetAvailableCitiesUseCase(cityRepository)
    }

    @Provides
    @Singleton
    fun provideUpdateSelectedCityUseCase(
        cityRepository: CityRepository
    ): UpdateSelectedCityUseCase {
        return UpdateSelectedCityUseCase(cityRepository)
    }

    @Provides
    @Singleton
    fun provideLanguageRepository(
        languagePreference: LanguagePreference
    ): LanguageRepository {
        return LanguageRepositoryImpl(languagePreference)
    }

    @Provides
    @Singleton
    fun providePermissionChecker(@ApplicationContext context: Context): PermissionChecker {
        return PermissionChecker(context)
    }

    @Provides
    @Singleton
    fun provideCheckLocationPermissionUseCase(repository: LocationRepository): CheckLocationPermissionUseCase {
        return CheckLocationPermissionUseCase(repository)
    }
}
