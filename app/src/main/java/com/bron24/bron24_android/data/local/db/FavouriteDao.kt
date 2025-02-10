package com.bron24.bron24_android.data.local.db

import com.bron24.bron24_android.domain.entity.favourite.Favourite
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

//@Dao
interface FavouriteDao {
//    @Query("SELECT * FROM favourite")
    fun getAll(): Flow<List<Favourite>>

//    @Query("DELETE FROM Favourite WHERE id = :id")
    fun delete(id:Int)

//    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(data:Favourite)
}

class FavouriteDaoImpl : FavouriteDao {
    override fun getAll(): Flow<List<Favourite>> {
        return flow {  }
    }

    override fun delete(id: Int) {

    }

    override fun insert(data: Favourite) {

    }

}