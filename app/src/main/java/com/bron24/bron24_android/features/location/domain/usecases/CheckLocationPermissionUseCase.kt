package com.bron24.bron24_android.location.domain.usecases

import com.bron24.bron24_android.location.domain.entities.LocationPermissionState
import com.bron24.bron24_android.location.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckLocationPermissionUseCase @Inject constructor(
    private val repository: LocationRepository
) {
    operator fun invoke(): Flow<LocationPermissionState> {
        return repository.checkLocationPermission()
    }
}
