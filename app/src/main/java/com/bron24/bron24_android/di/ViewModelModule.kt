package com.bron24.bron24_android.di

import com.bron24.bron24_android.screens.cityselection.domain.usecases.GetAvailableCitiesUseCase
import com.bron24.bron24_android.screens.cityselection.domain.usecases.UpdateSelectedCityUseCase
import com.bron24.bron24_android.screens.cityselection.presentation.CityViewModel
import com.bron24.bron24_android.screens.home.HomeViewModel
import com.bron24.bron24_android.screens.language.LanguageModel
import com.bron24.bron24_android.screens.language.LanguageViewModel
import com.bron24.bron24_android.screens.location.LocationModel
import com.bron24.bron24_android.screens.location.LocationViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object ViewModelModule {

    @Provides
    @ActivityScoped
    fun provideLanguageViewModel(
        languageModel: LanguageModel
    ): LanguageViewModel {
        return LanguageViewModel(languageModel)
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
        locationModel: LocationModel
    ): LocationViewModel {
        return LocationViewModel(locationModel)
    }

    @Provides
    @ActivityScoped
    fun provideHomeViewModel(
    ): HomeViewModel {
        return HomeViewModel()
    }
}
