package com.bron24.bron24_android.screens.map

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bron24.bron24_android.domain.entity.user.Location
import com.bron24.bron24_android.domain.entity.user.LocationPermissionState
import com.bron24.bron24_android.domain.entity.venue.VenueCoordinates
import com.bron24.bron24_android.domain.entity.venue.VenueDetails
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.catch

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

    private val _cameraPosition = MutableStateFlow<CameraPosition?>(null)
    val cameraPosition: StateFlow<CameraPosition?> = _cameraPosition

    private val _venueDetails = MutableStateFlow<VenueDetails?>(null)
    val venueDetails: StateFlow<VenueDetails?> = _venueDetails

    private val _selectedVenueId = MutableStateFlow<Int?>(null)
    val selectedVenueId: StateFlow<Int?> = _selectedVenueId

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        fetchVenuesForMap()
        checkLocationPermission()
    }

    fun centerOnVenue(venueId: Int) {
        viewModelScope.launch {
            val venue = _venues.value.find { it.venueId == venueId }
            venue?.let {
                centerOnCoordinates(it.latitude.toDouble(), it.longitude.toDouble())
            }
        }
    }

    private fun fetchVenuesForMap() {
        viewModelScope.launch {
            val venuesList = model.getVenuesCoordinates()
            _venues.value = venuesList
        }
    }

    private fun updateCurrentLocation() {
        viewModelScope.launch {
            model.getCurrentLocation()
                .catch { e ->
                    _error.value = "Error getting current location: ${e.message}"
                    Log.e("VenueMapViewModel", "Error getting current location", e)
                }
                .collect { location ->
                    _currentLocation.value = location
                    centerOnCurrentLocation()
                }
        }
    }

    private fun checkLocationPermission() {
        viewModelScope.launch {
            model.checkLocationPermission()
                .catch { e ->
                    _error.value = "Error checking location permission: ${e.message}"
                    Log.e("VenueMapViewModel", "Error checking location permission", e)
                }
                .collect { permissionState ->
                    _locationPermissionState.value = permissionState
                    if (permissionState == LocationPermissionState.GRANTED) {
                        updateCurrentLocation()
                    }
                }
        }
    }

    private fun fetchVenueDetails(venueId: Int) {
        viewModelScope.launch {
            model.getVenueDetails(venueId)
                .catch { e ->
                    _error.value = "Error fetching venue details: ${e.message}"
                    Log.e("VenueMapViewModel", "Error fetching venue details", e)
                }
                .collect { details ->
                    _venueDetails.value = details
                }
        }
    }

    fun centerOnCoordinates(latitude: Double, longitude: Double) {
        _cameraPosition.value = CameraPosition(Point(latitude, longitude), 15f, 0f, 0f)
    }

    fun zoomIn() {
        _cameraPosition.value?.let { currentPosition ->
            _cameraPosition.value = CameraPosition(
                currentPosition.target,
                currentPosition.zoom + 1f,
                currentPosition.azimuth,
                currentPosition.tilt
            )
        }
    }

    fun zoomOut() {
        _cameraPosition.value?.let { currentPosition ->
            _cameraPosition.value = CameraPosition(
                currentPosition.target,
                currentPosition.zoom - 1f,
                currentPosition.azimuth,
                currentPosition.tilt
            )
        }
    }

    fun centerOnCurrentLocation() {
        _currentLocation.value?.let { location ->
            centerOnCoordinates(location.latitude, location.longitude)
        }
    }

    fun updateCameraPosition(newPosition: CameraPosition) {
        _cameraPosition.value = newPosition
    }

    fun selectVenue(venueId: Int) {
        _selectedVenueId.value = venueId
        fetchVenueDetails(venueId)
    }

    fun clearSelectedVenue() {
        _selectedVenueId.value = null
        _venueDetails.value = null
    }

    fun clearError() {
        _error.value = null
    }
}