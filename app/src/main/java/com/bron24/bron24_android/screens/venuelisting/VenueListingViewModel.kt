package com.bron24.bron24_android.screens.venuelisting

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bron24.bron24_android.domain.entity.venue.Venue
import com.bron24.bron24_android.domain.usecases.venue.GetVenuesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
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
            val venueList = getVenuesUseCase.execute()
            _venues.value = venueList
            _isLoading.value = false
            Log.d("IMAGES_LIST", venueList.first().imageUrls.toString())
        }
    }
}