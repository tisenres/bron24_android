package com.bron24.bron24_android.screens.profile

import com.bron24.bron24_android.domain.entity.user.User

sealed class ProfileState() {
    data object Loading : ProfileState()
    data object Initial : ProfileState()
    data class Success(val user: User) : ProfileState()
}