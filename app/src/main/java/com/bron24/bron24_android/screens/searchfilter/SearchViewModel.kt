package com.bron24.bron24_android.screens.searchfilter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bron24.bron24_android.domain.entity.user.User
import com.bron24.bron24_android.domain.entity.venue.Venue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchModel: SearchModel
) : ViewModel() {

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

    private val _venues = MutableStateFlow<List<Venue>>(emptyList())
    val venues = _venues.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    fun getVenuesByQuery(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                searchModel.getVenueByQuery(
                    query = query,
                ).collect { venues ->
                    _isLoading.value = false
                    _venues.value = venues
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

}