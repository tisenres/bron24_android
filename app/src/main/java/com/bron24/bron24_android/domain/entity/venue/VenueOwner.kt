package com.bron24.bron24_android.domain.entity.venue

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VenueOwner(
    val id: Int,
    val ownerName: String,
    val tinNumber: Int,
    val contact1: String,
    val contact2: String
):Parcelable