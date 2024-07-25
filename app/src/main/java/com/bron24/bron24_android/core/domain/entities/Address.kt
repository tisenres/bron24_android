package com.bron24.bron24_android.core.domain.entities

data class Address(
    val id: Int,
    val addressName: String,
    val longitude: String,
    val latitude: String,
    val district: String,
    val closestMetroStation: String
)