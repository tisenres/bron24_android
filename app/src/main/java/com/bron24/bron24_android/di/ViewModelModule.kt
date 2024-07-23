package com.bron24.bron24_android.di

import android.content.Context
import com.bron24.bron24_android.features.cityselection.domain.usecases.GetAvailableCitiesUseCase
import com.bron24.bron24_android.features.cityselection.domain.usecases.UpdateSelectedCityUseCase
import com.bron24.bron24_android.features.cityselection.presentation.CityViewModel
import com.bron24.bron24_android.features.home.presentation.HomeViewModel
import com.bron24.bron24_android.features.language.domain.usecases.GetAvailableLanguagesUseCase
import com.bron24.bron24_android.features.language.domain.usecases.UpdateSelectedLanguageUseCase
import com.bron24.bron24_android.features.language.presentation.LanguageViewModel
import com.bron24.bron24_android.location.domain.usecases.CheckLocationPermissionUseCase
import com.bron24.bron24_android.features.location.presentation.LocationViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object ViewModelModule {

    @Provides
    @ActivityScoped
    fun provideLanguageViewModel(
        getAvailableLanguagesUseCase: GetAvailableLanguagesUseCase,
        updateSelectedLanguageUseCase: UpdateSelectedLanguageUseCase,
    ): LanguageViewModel {
        return LanguageViewModel(
            getAvailableLanguagesUseCase,
            updateSelectedLanguageUseCase,
        )
    }

    @Provides
    @ActivityScoped
    fun provideCityViewModel(
        getAvailableCitiesUseCase: GetAvailableCitiesUseCase,
        updateSelectedCityUseCase: UpdateSelectedCityUseCase
    ): CityViewModel {
        return CityViewModel(
            getAvailableCitiesUseCase,
            updateSelectedCityUseCase
        )
    }

    @Provides
    @ActivityScoped
    fun provideLocationViewModel(
        checkLocationPermissionUseCase: CheckLocationPermissionUseCase,
    ): LocationViewModel {
        return LocationViewModel(
            checkLocationPermissionUseCase
        )
    }

    @Provides
    @ActivityScoped
    fun provideHomeViewModel(
    ): HomeViewModel {
        return HomeViewModel()
    }
}
