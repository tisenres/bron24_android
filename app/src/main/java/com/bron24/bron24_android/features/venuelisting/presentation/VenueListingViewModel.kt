package com.bron24.bron24_android.features.venuelisting.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bron24.bron24_android.core.domain.entities.Venue
import com.bron24.bron24_android.core.domain.usecases.GetVenuesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VenueListingViewModel @Inject constructor(
    private val getVenuesUseCase: GetVenuesUseCase
) : ViewModel() {

    private val _venues = MutableStateFlow<List<Venue>>(emptyList())
    val venues: StateFlow<List<Venue>> = _venues

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        fetchVenues()
    }

    private fun fetchVenues() {
        viewModelScope.launch {
            _isLoading.value = true
            val venueList = getVenuesUseCase()
            _venues.value = venueList
            _isLoading.value = false
        }
    }
}