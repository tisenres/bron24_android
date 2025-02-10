//package com.bron24.bron24_android.data.local.db
//
//import androidx.room.Dao
//import androidx.room.Insert
//import androidx.room.OnConflictStrategy
//import androidx.room.Query
//import androidx.room.Transaction
//import kotlinx.coroutines.flow.Flow
//
//@Dao
//interface VenueDao {
//    @Transaction
//    @Query("SELECT * FROM venues")
//    fun getVenues(): Flow<List<VenueWithAddress>>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertVenues(venues: List<VenueEntity>)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertAddresses(addresses: List<AddressEntity>)
//
//    @Query("SELECT * FROM venue_pictures WHERE venueId = :venueId LIMIT 1")
//    fun getFirstVenuePicture(venueId: Int): Flow<VenuePictureEntity?>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertVenuePicture(picture: VenuePictureEntity)
//}