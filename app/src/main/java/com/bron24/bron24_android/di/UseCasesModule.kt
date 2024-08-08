package com.bron24.bron24_android.di

import android.content.Context
import com.bron24.bron24_android.data.local.preference.AppPreference
import com.bron24.bron24_android.data.network.apiservices.AuthApiService
import com.bron24.bron24_android.data.repository.AuthRepositoryImpl
import com.bron24.bron24_android.data.repository.LanguageRepositoryImpl
import com.bron24.bron24_android.data.repository.LocationRepositoryImpl
import com.bron24.bron24_android.data.repository.TokenRepositoryImpl
import com.bron24.bron24_android.domain.usecases.auth.*
import com.bron24.bron24_android.domain.usecases.language.*
import com.bron24.bron24_android.domain.usecases.location.*
import com.bron24.bron24_android.domain.repository.*
import com.bron24.bron24_android.helper.util.PermissionChecker
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
    fun provideRequestOTPUseCase(authRepository: AuthRepository): RequestOTPUseCase {
        return RequestOTPUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideVerifyOTPUseCase(authRepository: AuthRepository): VerifyOTPUseCase {
        return VerifyOTPUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideSaveTokenUseCase(tokenRepository: TokenRepository): SaveTokenUseCase {
        return SaveTokenUseCase(tokenRepository)
    }


    // Repositories - Start

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
        otpService: AuthApiService,
    ): AuthRepository {
        return AuthRepositoryImpl(otpService)
    }

    @Provides
    @Singleton
    fun bindLocationRepository(
        @ApplicationContext context: Context,
        permissionChecker: PermissionChecker
    ): LocationRepository {
        return LocationRepositoryImpl(context, permissionChecker)
    }

    @Provides
    @Singleton
    fun provideTokenRepository(
        appPreference: AppPreference
    ): TokenRepository {
        return TokenRepositoryImpl(appPreference)
    }

    // Repositories - End
}