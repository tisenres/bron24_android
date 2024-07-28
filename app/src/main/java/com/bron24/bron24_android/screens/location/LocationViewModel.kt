package com.bron24.bron24_android.screens.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bron24.bron24_android.domain.entity.enums.LocationPermissionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val locationModel: LocationModel
): ViewModel() {

    private val _locationPermissionState = MutableStateFlow(LocationPermissionState.DENIED)
//    val locationPermissionState: StateFlow<LocationPermissionState> = _locationPermissionState

    fun checkLocationPermission() {
        viewModelScope.launch {
            locationModel.checkLocationPermission().collect { permissionState ->
                _locationPermissionState.value = permissionState
            }
        }
    }

    fun setLocationPermissionGranted(granted: Boolean) {
        _locationPermissionState.value = if (granted) {
            LocationPermissionState.GRANTED
        } else {
            LocationPermissionState.DENIED
        }
    }
}
