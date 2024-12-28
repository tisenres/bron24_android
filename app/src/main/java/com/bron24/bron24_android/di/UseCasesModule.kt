package com.bron24.bron24_android.di

import android.content.Context
import com.bron24.bron24_android.data.local.preference.AppPreference
import com.bron24.bron24_android.data.local.preference.LocalStorage
import com.bron24.bron24_android.data.network.apiservices.AuthApi
import com.bron24.bron24_android.data.network.apiservices.BookingApiService
import com.bron24.bron24_android.data.network.apiservices.OrdersApi
import com.bron24.bron24_android.data.network.apiservices.VenueApiService
import com.bron24.bron24_android.data.repository.AuthRepositoryImpl
import com.bron24.bron24_android.data.repository.BookingRepositoryImpl
import com.bron24.bron24_android.data.repository.LanguageRepositoryImpl
import com.bron24.bron24_android.data.repository.LocationRepositoryImpl
import com.bron24.bron24_android.data.repository.OrdersRepositoryImpl
import com.bron24.bron24_android.data.repository.TokenRepositoryImpl
import com.bron24.bron24_android.data.repository.VenueRepositoryImpl
import com.bron24.bron24_android.domain.usecases.auth.*
import com.bron24.bron24_android.domain.usecases.language.*
import com.bron24.bron24_android.domain.usecases.location.*
import com.bron24.bron24_android.domain.repository.*
import com.bron24.bron24_android.domain.usecases.booking.ConfirmBookingUseCase
import com.bron24.bron24_android.domain.usecases.booking.CreateBookingUseCase
import com.bron24.bron24_android.domain.usecases.booking.GetBookingDetailsUseCase
import com.bron24.bron24_android.domain.usecases.venue.GetVenueDetailsUseCase
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
    fun provideGetVenueDetailsUseCase(
        venueRepository: VenueRepository,
        getCurrentLocationUseCase: GetCurrentLocationUseCase,
        checkLocationPermissionUseCase: CheckLocationPermissionUseCase
    ): GetVenueDetailsUseCase {
        return GetVenueDetailsUseCase(
            venueRepository,
            getCurrentLocationUseCase,
            checkLocationPermissionUseCase
        )
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
    fun provideGetAvailableDatesUseCase(repository: BookingRepository): GetBookingDetailsUseCase {
        return GetBookingDetailsUseCase(repository)
    }

    @Provides
    fun provideCreateBookingUseCase(
        bookingRepository: BookingRepository,
        preferencesRepository: PreferencesRepository,
        localStorage: LocalStorage
    ): CreateBookingUseCase {
        return CreateBookingUseCase(bookingRepository, preferencesRepository,localStorage)
    }

    @Provides
    fun provideConfirmBookingUseCase(
        bookingRepository: BookingRepository,
        preferencesRepository: PreferencesRepository
    ): ConfirmBookingUseCase {
        return ConfirmBookingUseCase(bookingRepository, preferencesRepository)
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
    fun provideVenueRepository(
        apiService: VenueApiService,
    ): VenueRepository {
        return VenueRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        authApi: AuthApi,
        tokenRepository: TokenRepository
    ): AuthRepository {
        return AuthRepositoryImpl(authApi, tokenRepository)
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

    @Provides
    @Singleton
    fun provideBookingRepository(
        bookingApiService: BookingApiService
    ): BookingRepository {
        return BookingRepositoryImpl(bookingApiService)
    }

    @Provides
    @Singleton
    fun provideOrdersRepository(
        ordersApi: OrdersApi
    ): OrdersRepository {
        return OrdersRepositoryImpl(ordersApi)
    }

//    @Module
//    @InstallIn(SingletonComponent::class)
//    object RepositoryModule {
//        @Provides
//        @Singleton
//        fun provideBookingRepository(apiService: BookingApiService): BookingRepository {
//            return BookingRepositoryImpl(apiService)
//        }
//    }

    // Repositories - End
}