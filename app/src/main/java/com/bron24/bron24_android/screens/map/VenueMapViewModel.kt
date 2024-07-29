package com.bron24.bron24_android.screens.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bron24.bron24_android.domain.entity.enums.LocationPermissionState
import com.bron24.bron24_android.domain.entity.user.Location
import com.bron24.bron24_android.domain.entity.venue.Venue
import com.bron24.bron24_android.domain.entity.venue.VenueCoordinates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VenueMapViewModel @Inject constructor(
    private val model: VenueMapModel
) : ViewModel() {
    private val _venues = MutableStateFlow<List<VenueCoordinates>>(emptyList())
    val venues: StateFlow<List<VenueCoordinates>> = _venues

    private val _currentLocation = MutableStateFlow<Location?>(null)
    val currentLocation: StateFlow<Location?> = _currentLocation

    private val _locationPermissionState = MutableStateFlow(LocationPermissionState.DENIED)
    val locationPermissionState: StateFlow<LocationPermissionState> = _locationPermissionState

    init {
        fetchVenuesForMap()
        checkLocationPermission()
    }

    private fun fetchVenuesForMap() {
        viewModelScope.launch {
            val venuesList = model.getVenuesCoordinatesUseCase()
            _venues.value = venuesList
        }
    }

    fun updateCurrentLocation() {
        viewModelScope.launch {
            model.getCurrentLocation().collect { location ->
                _currentLocation.value = location
            }
        }
    }

    private fun checkLocationPermission() {
        viewModelScope.launch {
            model.checkLocationPermission().collect { permissionState ->
                _locationPermissionState.value = permissionState
                if (permissionState == LocationPermissionState.GRANTED) {
                    updateCurrentLocation()
                }
            }
        }
    }

    fun onVenueTapped(venue: Venue) {
        // TODO: Handle venue tap
    }
}