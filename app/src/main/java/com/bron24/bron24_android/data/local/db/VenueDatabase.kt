package com.bron24.bron24_android.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bron24.bron24_android.domain.entity.favourite.Favourite

@Database(entities = [VenueEntity::class, AddressEntity::class, VenuePictureEntity::class,Favourite::class], version = 1)
abstract class VenueDatabase : RoomDatabase() {
    abstract fun venueDao(): VenueDao
    abstract fun favouriteDao():FavouriteDao
}

