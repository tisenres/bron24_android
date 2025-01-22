package com.bron24.bron24_android.domain.entity.booking

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TimeSlot(
    val startTime: String,
    val endTime: String,
    val isAvailable: Boolean = true,
    val isSelected: Boolean = false,
    val hours: Int? = 1
):Parcelable
