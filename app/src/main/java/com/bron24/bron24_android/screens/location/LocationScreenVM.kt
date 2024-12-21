package com.bron24.bron24_android.screens.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bron24.bron24_android.domain.entity.user.LocationPermissionState
import com.bron24.bron24_android.domain.usecases.location.CheckLocationPermissionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationScreenVM @Inject constructor(
): ViewModel() {

    private val _locationPermissionState = MutableStateFlow(LocationPermissionState.DENIED)

    fun checkLocationPermission() {
        viewModelScope.launch {
//            model.checkLocationPermission().collect { permissionState ->
//                _locationPermissionState.value = permissionState
//            }
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

//class LocationModel @Inject constructor(
//    private val checkLocationPermissionUseCase: CheckLocationPermissionUseCase,
//) {
//    fun checkLocationPermission(): Flow<LocationPermissionState> {
//        return checkLocationPermissionUseCase.execute()
//    }
//}
