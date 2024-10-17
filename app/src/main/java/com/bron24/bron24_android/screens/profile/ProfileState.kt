package com.bron24.bron24_android.screens.profile

import com.bron24.bron24_android.domain.entity.user.User

sealed class ProfileState {
    object Loading : ProfileState()
    object Initial : ProfileState()
    data class Success(val user: User) : ProfileState()
    data class Error(val message: String) : ProfileState()
}