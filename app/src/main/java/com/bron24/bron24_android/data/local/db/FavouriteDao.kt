package com.bron24.bron24_android.data.local.db

import androidx.room.Dao
import androidx.room.Query
import com.bron24.bron24_android.domain.entity.favourite.Favourite
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteDao {
    @Query("SELECT * FROM favourite")
    fun getAll(): Flow<List<Favourite>>
}