package com.bron24.bron24_android.domain.entity.user

import com.bron24.bron24_android.domain.entity.enums.UserType

data class User(
    val userId: String?,
    val firstName: String,
    val secondName: String,
    val email: String?,
    val phoneNumber: String,
    val userType: UserType = UserType.CUSTOMER
)