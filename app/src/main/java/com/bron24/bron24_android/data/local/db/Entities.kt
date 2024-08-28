package com.bron24.bron24_android.data.local.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Embedded
import androidx.room.Relation

@Entity(tableName = "venues")
data class VenueEntity(
    @PrimaryKey val venueId: Int,
    val venueName: String,
    val pricePerHour: String,
    val addressId: Int
)

@Entity(tableName = "addresses")
data class AddressEntity(
    @PrimaryKey val id: Int,
    val addressName: String,
    val district: String,
    val closestMetroStation: String
)

@Entity(tableName = "venue_pictures")
data class VenuePictureEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val venueId: Int,
    val imageUrl: String
)

data class VenueWithAddress(
    @Embedded val venue: VenueEntity,
    @Relation(
        parentColumn = "addressId",
        entityColumn = "id"
    )
    val address: AddressEntity
)