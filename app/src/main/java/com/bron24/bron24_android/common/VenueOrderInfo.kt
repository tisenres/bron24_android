package com.bron24.bron24_android.common

import android.os.Parcelable
import com.bron24.bron24_android.domain.entity.booking.TimeSlot
import kotlinx.parcelize.Parcelize

@Parcelize
data class VenueOrderInfo(
    val venueId: Int,
    val date: String,
    val sector: String,
    val timeSlots: List<TimeSlot>,
):Parcelable
