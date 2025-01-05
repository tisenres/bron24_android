package com.bron24.bron24_android.domain.entity.venue

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Infrastructure(
    val id: Int,
    val name: String,
    val description: String,
    val picture: Boolean,
):Parcelable