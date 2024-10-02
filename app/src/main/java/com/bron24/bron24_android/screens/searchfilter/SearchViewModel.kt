package com.bron24.bron24_android.screens.searchfilter

import androidx.lifecycle.ViewModel
import com.bron24.bron24_android.domain.entity.user.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchModel: SearchModel
): ViewModel() {

    private val _personalUserData = MutableStateFlow<User?>(null)
    private val personalUserData = _personalUserData.asStateFlow()

    private val _firstName = MutableStateFlow<String?>("")
    val firstName = _firstName.asStateFlow()

    init {
        getPersonalUserData()
    }

    private fun getPersonalUserData() {
        _personalUserData.value = searchModel.getPersonalUserData()
        _firstName.value = _personalUserData.value?.firstName
    }
}