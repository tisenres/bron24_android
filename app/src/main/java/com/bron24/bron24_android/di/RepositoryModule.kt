package com.bron24.bron24_android.di

import com.bron24.bron24_android.data.repository.*
import com.bron24.bron24_android.domain.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @[Binds Singleton]
    fun bindPreferencesRepository(
        preferencesRepositoryImpl: PreferencesRepositoryImpl
    ): PreferencesRepository

    @[Binds Singleton]
    fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository

    @[Binds Singleton]
    fun bindBookingRepository(
        impl: BookingRepositoryImpl
    ): BookingRepository

    @[Binds Singleton]
    fun bindLanguageRepository(
        impl: LanguageRepositoryImpl
    ): LanguageRepository

    @[Binds Singleton]
    fun bindLocationRepository(
        impl: LocationRepositoryImpl
    ): LocationRepository

    @[Binds Singleton]
    fun bindOrderRepository(
        impl: OrdersRepositoryImpl
    ): OrdersRepository

    @[Binds Singleton]
    fun bindTokenRepository(
        impl: TokenRepositoryImpl
    ): TokenRepository

    @[Binds Singleton]
    fun bindVenueRepository(
        impl: VenueRepositoryImpl
    ): VenueRepository

    @[Binds Singleton]
    fun bindProfileRepository(
        impl: ProfileRepositoryImpl
    ): ProfileRepository

}