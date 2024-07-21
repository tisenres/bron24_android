package com.bron24.bron24_android.location.domain.repository

import com.bron24.bron24_android.location.domain.entities.LocationPermissionState
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    fun checkLocationPermission(): Flow<LocationPermissionState>
}