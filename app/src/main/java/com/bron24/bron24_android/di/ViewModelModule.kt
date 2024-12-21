package com.bron24.bron24_android.di

import com.bron24.bron24_android.domain.usecases.onboarding.OnboardingUseCase
import com.bron24.bron24_android.screens.menu_pages.home_page.HomePageVM
import com.bron24.bron24_android.screens.location.LocationScreenVM
import com.bron24.bron24_android.screens.main.MainViewModel
import com.bron24.bron24_android.screens.map.VenueMapModel
import com.bron24.bron24_android.screens.map.VenueMapViewModel
import com.bron24.bron24_android.screens.venuedetails.VenueDetailsModel
import com.bron24.bron24_android.screens.venuedetails.VenueDetailsViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object ViewModelModule {

//    @Provides
//    @ActivityScoped
//    fun provideLanguageViewModel(
//        languageModel: LanguageModel
//    ): LanguageViewModel {
//        return LanguageViewModel(languageModel)
//    }

    @Provides
    @ActivityScoped
    fun provideLocationViewModel(
        locationModel: LocationModel
    ): LocationScreenVM {
        return LocationScreenVM(locationModel)
    }

    @Provides
    @ActivityScoped
    fun provideHomeViewModel(
    ): HomePageVM {
        return HomePageVM()
    }

    @Provides
    @ActivityScoped
    fun provideVenueMapViewModel(
        venueMapModel: VenueMapModel
    ): VenueMapViewModel {
        return VenueMapViewModel(venueMapModel)
    }

    @Provides
    @ActivityScoped
    fun provideVenueDetailsViewModel(
        venueDetailsModel: VenueDetailsModel
    ): VenueDetailsViewModel {
        return VenueDetailsViewModel(venueDetailsModel)
    }

    @Provides
    @ActivityScoped
    fun provideMainViewModel(
        onboardingUseCase: OnboardingUseCase
    ): MainViewModel {
        return MainViewModel(onboardingUseCase)
    }
}