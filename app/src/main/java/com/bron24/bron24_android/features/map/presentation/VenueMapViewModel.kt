package com.bron24.bron24_android.features.map.presentation

import com.bron24.bron24_android.core.domain.usecases.GetVenuesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bron24.bron24_android.core.domain.entities.Venue
import kotlinx.coroutines.launch

@HiltViewModel
class VenueMapViewModel @Inject constructor(
    private val getVenuesUseCase: GetVenuesUseCase
) : ViewModel() {
    private val _venues = MutableLiveData<List<Venue>>()
    val venues: LiveData<List<Venue>> = _venues

    init {
        fetchVenuesForMap()
    }

    private fun fetchVenuesForMap() {
        viewModelScope.launch {
            val venuesList = getVenuesUseCase()
            _venues.value = venuesList
        }
    }
}

