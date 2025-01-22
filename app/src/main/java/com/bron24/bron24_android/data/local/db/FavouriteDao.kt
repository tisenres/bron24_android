package com.bron24.bron24_android.data.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bron24.bron24_android.domain.entity.favourite.Favourite
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteDao {
    @Query("SELECT * FROM favourite")
    fun getAll(): Flow<List<Favourite>>

    @Query("DELETE FROM Favourite WHERE id = :id")
    fun delete(id:Int)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(data:Favourite)
}