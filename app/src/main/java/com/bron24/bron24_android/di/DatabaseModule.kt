package com.bron24.bron24_android.di

import android.content.Context
import androidx.room.Room
import com.bron24.bron24_android.data.local.db.FavouriteDao
import com.bron24.bron24_android.data.local.db.VenueDatabase
import com.bron24.bron24_android.data.local.db.VenueDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideVenueDatabase(@ApplicationContext context: Context): VenueDatabase {
        return Room.databaseBuilder(
            context,
            VenueDatabase::class.java,
            "venue_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideVenueDao(database: VenueDatabase): VenueDao {
        return database.venueDao()
    }
    @Provides
    @Singleton
    fun provideFavouriteDao(database: VenueDatabase): FavouriteDao = database.favouriteDao()
}