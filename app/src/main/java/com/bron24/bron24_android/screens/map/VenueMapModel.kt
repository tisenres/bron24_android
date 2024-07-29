package com.bron24.bron24_android.screens.map

import com.bron24.bron24_android.domain.entity.enums.LocationPermissionState
import com.bron24.bron24_android.domain.entity.user.Location
import com.bron24.bron24_android.domain.entity.venue.Venue
import com.bron24.bron24_android.domain.entity.venue.VenueCoordinates
import com.bron24.bron24_android.domain.usecases.location.CheckLocationPermissionUseCase
import com.bron24.bron24_android.domain.usecases.location.GetCurrentLocationUseCase
import com.bron24.bron24_android.domain.usecases.venue.GetVenuesCoordinatesUseCase
import com.bron24.bron24_android.domain.usecases.venue.GetVenuesUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class VenueMapModel @Inject constructor(
    private val getVenuesCoordinatesUseCase: GetVenuesCoordinatesUseCase,
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase,
    private val checkLocationPermissionUseCase: CheckLocationPermissionUseCase
) {

    fun getCurrentLocation(): Flow<Location> {
        return getCurrentLocationUseCase.execute()
    }

    fun checkLocationPermission(): Flow<LocationPermissionState> {
        return checkLocationPermissionUseCase.execute()
    }

    suspend fun getVenuesCoordinatesUseCase(): List<VenueCoordinates> {
        return getVenuesCoordinatesUseCase.execute()
    }
}