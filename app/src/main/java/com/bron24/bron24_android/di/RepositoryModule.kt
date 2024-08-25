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
abstract class RepositoryModule {

//    @Binds
//    @Singleton
//    abstract fun bindVenueRepository(
//        venueRepositoryImpl: VenueRepositoryImpl
//    ): VenueRepository

    @Binds
    @Singleton
    abstract fun bindPreferencesRepository(
        preferencesRepositoryImpl: PreferencesRepositoryImpl
    ): PreferencesRepository
}