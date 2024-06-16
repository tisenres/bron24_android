package com.bron24.bron24_android.features.location.presentation

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LocationViewModel(application: Application) : AndroidViewModel(application) {

    private val _locationPermissionGranted = MutableStateFlow(false)
    val locationPermissionGranted: StateFlow<Boolean> = _locationPermissionGranted

    fun checkLocationPermission() {
        val context = getApplication<Application>().applicationContext
        val permission = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        _locationPermissionGranted.value = permission == PackageManager.PERMISSION_GRANTED
    }

    fun setLocationPermissionGranted(granted: Boolean) {
        viewModelScope.launch {
            _locationPermissionGranted.value = granted
        }
    }
}