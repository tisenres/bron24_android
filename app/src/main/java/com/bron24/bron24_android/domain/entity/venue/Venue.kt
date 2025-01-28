package com.bron24.bron24_android.domain.entity.venue

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Venue(
    val venueId: Int,
    val venueName: String,
    val pricePerHour: String,
    val address: Address,
    val rate:Double,
    val distance: Double,
    val slots:Int,
    val previewImage: String?,
    val favourite:Boolean,
):Parcelable