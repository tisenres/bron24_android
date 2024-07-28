package com.bron24.bron24_android.domain.usecases.location

import com.bron24.bron24_android.domain.repository.LocationRepository
import com.bron24.bron24_android.features.location.domain.entities.LocationPermissionState
import kotlinx.coroutines.flow.Flow

class CheckLocationPermissionUseCase(private val locationRepository: LocationRepository) {

    fun execute(): Flow<LocationPermissionState> {
        return locationRepository.checkLocationPermission()
    }
}
