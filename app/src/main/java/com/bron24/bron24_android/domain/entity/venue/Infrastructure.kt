package com.bron24.bron24_android.domain.entity.venue

data class Infrastructure(
    val id: Int,
    val lockerRoom: Boolean,
    val stands: String,
    val shower: Boolean,
    val parking: Boolean
)
