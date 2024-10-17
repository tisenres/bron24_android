package com.bron24.bron24_android.screens.profile

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val model: ProfileModel
): ViewModel() {

    private val _profileState = MutableStateFlow<ProfileState>(ProfileState.Initial)
    val profileState = _profileState.asStateFlow()

    fun getPersonalUserData() {
        _profileState.value = ProfileState.Loading
        _profileState.value = ProfileState.Success(model.getPersonalUserData())
    }

}