package com.bron24.bron24_android.features.location.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bron24.bron24_android.features.location.domain.entities.LocationPermissionState
import com.bron24.bron24_android.features.location.domain.usecases.CheckLocationPermissionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val checkLocationPermissionUseCase: CheckLocationPermissionUseCase
) : ViewModel() {

    private val _locationPermissionState = MutableStateFlow(LocationPermissionState.Denied)
    val locationPermissionState: StateFlow<LocationPermissionState> = _locationPermissionState

    fun checkLocationPermission() {
        viewModelScope.launch {
            checkLocationPermissionUseCase().collect { permissionState ->
                _locationPermissionState.value = permissionState
            }
        }
    }

    fun setLocationPermissionGranted(granted: Boolean) {
        _locationPermissionState.value = if (granted) {
            LocationPermissionState.Granted
        } else {
            LocationPermissionState.Denied
        }
    }
}
