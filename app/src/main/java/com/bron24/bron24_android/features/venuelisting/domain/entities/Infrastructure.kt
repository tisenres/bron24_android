package com.bron24.bron24_android.features.venuelisting.domain.entities

data class Infrastructure(
    val id: Int,
    val lockerRoom: Boolean,
    val stands: String,
    val shower: Boolean,
    val parking: Boolean
)
