package com.bron24.bron24_android.domain.entity.venue

data class Address(
    val id: Int,
    val addressName: String,
    val longitude: Double,
    val latitude: Double,
    val district: String,
    val closestMetroStation: String
)