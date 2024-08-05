package com.bron24.bron24_android.di

import android.content.Context
import com.bron24.bron24_android.data.PermissionChecker
import com.bron24.bron24_android.data.local.preference.AppPreference
import com.bron24.bron24_android.data.network.apiservices.OTPApiService
import com.bron24.bron24_android.data.repository.AuthRepositoryImpl
import com.bron24.bron24_android.data.repository.LanguageRepositoryImpl
import com.bron24.bron24_android.data.repository.LocationRepositoryImpl
import com.bron24.bron24_android.domain.repository.AuthRepository
import com.bron24.bron24_android.domain.repository.LanguageRepository
import com.bron24.bron24_android.domain.repository.LocationRepository
import com.bron24.bron24_android.domain.usecases.language.GetAvailableLanguagesUseCase
import com.bron24.bron24_android.domain.usecases.language.GetSelectedLanguageUseCase
import com.bron24.bron24_android.domain.usecases.language.SetUserLanguageUseCase
import com.bron24.bron24_android.domain.usecases.location.CheckLocationPermissionUseCase
import com.bron24.bron24_android.domain.usecases.location.GetCurrentLocationUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {

    @Provides
    @Singleton
    fun provideGetAvailableLanguagesUseCase(
        languageRepository: LanguageRepository
    ): GetAvailableLanguagesUseCase {
        return GetAvailableLanguagesUseCase(languageRepository)
    }

    @Provides
    @Singleton
    fun provideSetSelectedLanguageUseCase(
        languageRepository: LanguageRepository
    ): SetUserLanguageUseCase {
        return SetUserLanguageUseCase(languageRepository)
    }

    @Provides
    @Singleton
    fun provideGetSelectedLanguageUseCase(
        languageRepository: LanguageRepository
    ): GetSelectedLanguageUseCase {
        return GetSelectedLanguageUseCase(languageRepository)
    }

    @Provides
    @Singleton
    fun provideCheckLocationPermissionUseCase(
        locationRepository: LocationRepository
    ): CheckLocationPermissionUseCase {
        return CheckLocationPermissionUseCase(locationRepository)
    }

    @Provides
    @Singleton
    fun provideGetCurrentLocationUseCase(
        repository: LocationRepository
    ): GetCurrentLocationUseCase {
        return GetCurrentLocationUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideLanguageRepository(
        appPreference: AppPreference
    ): LanguageRepository {
        return LanguageRepositoryImpl(appPreference)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        otpService: OTPApiService,
        appPreference: AppPreference
    ): AuthRepository {
        return AuthRepositoryImpl(otpService, appPreference)
    }

    @Provides
    @Singleton
    fun bindLocationRepository(
        @ApplicationContext context: Context,
        permissionChecker: PermissionChecker
    ): LocationRepository {
        return LocationRepositoryImpl(context, permissionChecker)
    }
}