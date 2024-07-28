package com.bron24.bron24_android.features.location

import com.bron24.bron24_android.domain.entity.enums.LocationPermissionState
import com.bron24.bron24_android.domain.usecases.location.CheckLocationPermissionUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocationModel @Inject constructor(
    private val checkLocationPermissionUseCase: CheckLocationPermissionUseCase
) {
    fun checkLocationPermission(): Flow<LocationPermissionState> {
        return checkLocationPermissionUseCase.execute()
    }
}