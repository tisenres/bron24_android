package com.bron24.bron24_android.features.location.domain.usecases

import com.bron24.bron24_android.features.location.data.LocationRepository
import com.bron24.bron24_android.features.location.domain.model.LocationPermissionState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckLocationPermissionUseCase @Inject constructor(
    private val repository: LocationRepository
) {
    fun execute(): Flow<LocationPermissionState> {
        return repository.checkLocationPermission()
    }
}