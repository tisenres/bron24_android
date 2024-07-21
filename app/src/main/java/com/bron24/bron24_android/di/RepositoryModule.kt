package com.bron24.bron24_android.di

import com.bron24.bron24_android.features.venuelisting.data.repository.VenueRepositoryImpl
import com.bron24.bron24_android.features.venuelisting.domain.repository.VenueRepository
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
}
