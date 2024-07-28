package com.bron24.bron24_android.screens.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bron24.bron24_android.domain.entity.venue.Venue
import com.bron24.bron24_android.domain.entity.user.Location
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VenueMapViewModel @Inject constructor(
    private val model: VenueMapModel
) : ViewModel() {
    private val _venues = MutableStateFlow<List<Venue>>(emptyList())
    val venues: StateFlow<List<Venue>> = _venues

    private val _currentLocation = MutableStateFlow<Location?>(null)
    val currentLocation: StateFlow<Location?> = _currentLocation

    init {
        fetchVenuesForMap()
    }

    private fun fetchVenuesForMap() {
        viewModelScope.launch {
            val venuesList = model.getVenuesUseCase()
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

//    fun checkLocationPermission() {
//        viewModelScope.launch {
//            model.getCurrentLocation().collect { permissionState ->
//                if (permissionState == LocationPermissionState.GRANTED) {
//                    updateCurrentLocation()
//                } else {
//                    requestLocationPermission()
//                }
//            }
//        }
//    }

    private fun requestLocationPermission() {
        // Implementation to request location permission
    }

    fun onVenueTapped(venue: Venue) {
        // _selectedVenue.value = venue
        // You can add more logic here, like navigating to a detail screen
    }
}