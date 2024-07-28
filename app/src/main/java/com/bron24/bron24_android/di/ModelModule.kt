package com.bron24.bron24_android.di

import com.bron24.bron24_android.domain.usecases.language.GetAvailableLanguagesUseCase
import com.bron24.bron24_android.domain.usecases.language.SetUserLanguageUseCase
import com.bron24.bron24_android.domain.usecases.location.CheckLocationPermissionUseCase
import com.bron24.bron24_android.domain.usecases.venue.GetVenuesUseCase
import com.bron24.bron24_android.screens.language.LanguageModel
import com.bron24.bron24_android.screens.location.LocationModel
import com.bron24.bron24_android.screens.map.VenueMapModel
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

    @Provides
    @Singleton
    fun provideLocationModel(
        checkLocationPermissionUseCase: CheckLocationPermissionUseCase
    ): LocationModel {
        return LocationModel(
            checkLocationPermissionUseCase
        )
    }


    @Provides
    @Singleton
    fun provideVenueMapModel(
        getVenuesUseCase: GetVenuesUseCase
    ): VenueMapModel {
        return VenueMapModel(
            getVenuesUseCase
        )
    }
}