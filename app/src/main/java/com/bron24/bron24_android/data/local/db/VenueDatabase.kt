package com.bron24.bron24_android.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [VenueEntity::class, AddressEntity::class, VenuePictureEntity::class], version = 1)
abstract class VenueDatabase : RoomDatabase() {
    abstract fun venueDao(): VenueDao
}