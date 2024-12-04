package com.bron24.bron24_android.common

data class FilterOptions(
    val availableTime: String? = null,
    val minPrice: Int? = null,
    val maxPrice: Int? = null,
    val infrastructure: Boolean? = null,
    val district: String? = null
)