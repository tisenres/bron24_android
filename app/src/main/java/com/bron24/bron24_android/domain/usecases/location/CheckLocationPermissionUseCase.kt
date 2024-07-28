package com.bron24.bron24_android.domain.usecases.location

import com.bron24.bron24_android.domain.entity.enums.LocationPermissionState
import com.bron24.bron24_android.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckLocationPermissionUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    fun execute(): Flow<LocationPermissionState> {
        return locationRepository.checkLocationPermission()
    }
}
