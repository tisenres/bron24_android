package com.bron24.bron24_android.di

import android.content.Context
import com.bron24.bron24_android.data.repository.VenueRepositoryImpl
import com.bron24.bron24_android.domain.repository.VenueRepository
import com.bron24.bron24_android.data.repository.LocationRepositoryImpl
import com.bron24.bron24_android.domain.repository.LocationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
