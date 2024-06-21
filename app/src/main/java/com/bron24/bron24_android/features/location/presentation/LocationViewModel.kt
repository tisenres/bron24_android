package com.bron24.bron24_android.features.location.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bron24.bron24_android.features.location.domain.model.LocationPermissionState
import com.bron24.bron24_android.features.location.domain.usecases.CheckLocationPermissionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    application: Application,
    private val checkLocationPermissionUseCase: CheckLocationPermissionUseCase
) : AndroidViewModel(application) {

    private val _locationPermissionState = MutableStateFlow<LocationPermissionState>(LocationPermissionState.Denied)
    val locationPermissionState: StateFlow<LocationPermissionState> = _locationPermissionState

    fun checkLocationPermission() {
        viewModelScope.launch {
            checkLocationPermissionUseCase.execute().collect { permissionState ->
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