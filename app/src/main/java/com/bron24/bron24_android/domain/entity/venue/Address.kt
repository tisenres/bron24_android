package com.bron24.bron24_android.domain.entity.venue

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class Address(
    val id: Int,
    val addressName: String,
    val district: String,
    val closestMetroStation: String
):Parcelable