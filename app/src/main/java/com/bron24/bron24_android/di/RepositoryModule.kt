package com.bron24.bron24_android.di

import com.bron24.bron24_android.features.venuelisting.data.repository.VenueRepositoryImpl
import com.bron24.bron24_android.core.domain.repository.VenueRepository
import com.bron24.bron24_android.location.data.repository.LocationRepositoryImpl
import com.bron24.bron24_android.location.domain.repository.LocationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindVenueRepository(
        venueRepositoryImpl: VenueRepositoryImpl
    ): VenueRepository

    @Binds
    @Singleton
    abstract fun bindLocationRepository(
        locationRepositoryImpl: LocationRepositoryImpl
    ): LocationRepository
}
