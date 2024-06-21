package com.bron24.bron24_android.features.location.data

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.bron24.bron24_android.features.location.domain.model.LocationPermissionState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val context: Context
) {
    fun checkLocationPermission(): Flow<LocationPermissionState> = flow {
        val permissionState = if (ContextCompat.checkSelfPermission(
                context, android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED) {
            LocationPermissionState.Granted
        } else {
            LocationPermissionState.Denied
        }
        emit(permissionState)
    }
}
