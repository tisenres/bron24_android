package com.bron24.bron24_android.domain.entity.venue

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Infrastructure(
    val id: Int,
    val lockerRoom: Boolean,
    val stands: String,
    val shower: Boolean,
    val parking: Boolean
):Parcelable