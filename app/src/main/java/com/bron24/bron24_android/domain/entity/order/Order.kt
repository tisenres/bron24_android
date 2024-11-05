package com.bron24.bron24_android.domain.entity.order

data class Order(
    val id: Long,
    val title: String,
    val price: Int,
    val date: String,
    val time: String,
    val location: String,
    val status: String
)