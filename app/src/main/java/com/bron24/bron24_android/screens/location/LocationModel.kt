package com.bron24.bron24_android.screens.location

import androidx.activity.result.contract.ActivityResultContracts
import com.bron24.bron24_android.domain.entity.enums.LocationPermissionState
import com.bron24.bron24_android.domain.usecases.location.CheckLocationPermissionUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocationModel @Inject constructor(
    private val checkLocationPermissionUseCase: CheckLocationPermissionUseCase,
) {
    fun checkLocationPermission(): Flow<LocationPermissionState> {
        return checkLocationPermissionUseCase.execute()
    }
}