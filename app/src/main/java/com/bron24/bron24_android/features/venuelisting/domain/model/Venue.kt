package com.bron24.bron24_android.features.venuelisting.domain.model

data class Venue(
    val name: String,
    val address: String,
    val distance: String,
    val rating: String,
    val price: String,
    val freeSlots: String,
    val imageUrl: String,
    val overlayImageUrl: String
)
