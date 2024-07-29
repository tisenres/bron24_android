package com.bron24.bron24_android.domain.entity.venue

data class Address(
    val id: Int,
    val addressName: String,
    val district: String,
    val closestMetroStation: String
)