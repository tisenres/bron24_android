package com.bron24.bron24_android.screens.location

import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bron24.bron24_android.domain.entity.enums.LocationPermissionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val model: LocationModel
): ViewModel() {

    private val _locationPermissionState = MutableStateFlow(LocationPermissionState.DENIED)

    fun checkLocationPermission() {
        viewModelScope.launch {
            model.checkLocationPermission().collect { permissionState ->
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
