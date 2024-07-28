package com.bron24.bron24_android.domain.repository

import com.bron24.bron24_android.domain.entity.user.Location
import com.bron24.bron24_android.features.location.domain.entities.LocationPermissionState
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    fun getCurrentLocation(): Location
    fun checkLocationPermission(): Flow<LocationPermissionState>
    fun requestLocationPermission()
}