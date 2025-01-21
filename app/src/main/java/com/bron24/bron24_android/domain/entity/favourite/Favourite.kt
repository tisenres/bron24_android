package com.bron24.bron24_android.domain.entity.favourite

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Favourite(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    @ColumnInfo(name = "venue_id")
    val venueId:Int
)