package com.bron24.bron24_android.di

import com.bron24.bron24_android.domain.usecases.auth.AuthenticateUserUseCase
import com.bron24.bron24_android.domain.usecases.auth.RequestOTPUseCase
import com.bron24.bron24_android.domain.usecases.auth.VerifyOTPUseCase
import com.bron24.bron24_android.domain.usecases.booking.ConfirmBookingUseCase
import com.bron24.bron24_android.domain.usecases.booking.CreateBookingUseCase
import com.bron24.bron24_android.domain.usecases.language.*
import com.bron24.bron24_android.domain.usecases.location.*
import com.bron24.bron24_android.domain.usecases.venue.*
import com.bron24.bron24_android.helper.util.LocaleManager
import com.bron24.bron24_android.screens.auth.AuthModel
import com.bron24.bron24_android.screens.booking.screens.confirmbooking.BookingConfirmationModel
import com.bron24.bron24_android.screens.map.VenueMapModel
import com.bron24.bron24_android.screens.venuedetails.VenueDetailsModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModelModule {

//    @Provides
//    @Singleton
//    fun provideLanguageModel(
//        getAvailableLanguagesUseCase: GetAvailableLanguagesUseCase,
//        setUserLanguageUseCase: SetUserLanguageUseCase,
//        localeManager: LocaleManager
//    ): LanguageModel {
//        return LanguageModel(
//            getAvailableLanguagesUseCase,
//            setUserLanguageUseCase,
//            localeManager
//        )
//    }

//    @Provides
//    @Singleton
//    fun provideLocationModel(
//        checkLocationPermissionUseCase: CheckLocationPermissionUseCase
//    ): LocationModel {
//        return LocationModel(
//            checkLocationPermissionUseCase
//        )
//    }


    @Provides
    @Singleton
    fun provideVenueMapModel(
        getVenuesCoordinatesUseCase: GetVenuesCoordinatesUseCase,
        getCurrentLocationUseCase: GetCurrentLocationUseCase,
        checkLocationPermissionUseCase: CheckLocationPermissionUseCase,
        getVenueDetailsUseCase: GetVenueDetailsUseCase
    ): VenueMapModel {
        return VenueMapModel(
            getVenuesCoordinatesUseCase,
            getCurrentLocationUseCase,
            checkLocationPermissionUseCase,
            getVenueDetailsUseCase
        )
    }

    @Provides
    @Singleton
    fun provideVenueDetailsModel(
        getVenueDetailsUseCase: GetVenueDetailsUseCase,
    ): VenueDetailsModel {
        return VenueDetailsModel(
            getVenueDetailsUseCase
        )
    }

    @Provides
    @Singleton
    fun provideAuthModel(
        requestOTPUseCase: RequestOTPUseCase,
        verifyOTPUseCase: VerifyOTPUseCase,
        authenticateUserUseCase: AuthenticateUserUseCase
    ): AuthModel {
        return AuthModel(
            requestOTPUseCase,
            verifyOTPUseCase,
            authenticateUserUseCase
        )
    }

    @Provides
    @Singleton
    fun provideBookingConfirmationModel(
        createBookingUseCase: CreateBookingUseCase,
        confirmBookingUseCase: ConfirmBookingUseCase
    ): BookingConfirmationModel {
        return BookingConfirmationModel(createBookingUseCase, confirmBookingUseCase)
    }
}