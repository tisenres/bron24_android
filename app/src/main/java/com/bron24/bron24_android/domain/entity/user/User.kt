package com.bron24.bron24_android.domain.entity.user

data class User(
    val userId: String? = "0",
    val firstName: String,
    val lastName: String,
    val email: String? = null,
    val phoneNumber: String,
    val userType: UserType = UserType.CUSTOMER
)