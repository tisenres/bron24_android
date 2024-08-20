package com.bron24.bron24_android.domain.repository

import com.bron24.bron24_android.domain.entity.enums.LocationPermissionState
import com.bron24.bron24_android.domain.entity.user.Location
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    fun getCurrentLocation(): Flow<Location>
    fun checkLocationPermission(): Flow<LocationPermissionState>
}