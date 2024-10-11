package com.bron24.bron24_android.domain.usecases.venue

import android.util.Log
import com.bron24.bron24_android.domain.entity.user.LocationPermissionState
import com.bron24.bron24_android.domain.entity.venue.Venue
import com.bron24.bron24_android.domain.repository.VenueRepository
import com.bron24.bron24_android.domain.usecases.location.CheckLocationPermissionUseCase
import com.bron24.bron24_android.domain.usecases.location.GetCurrentLocationUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

private const val TAG = "GetVenuesUseCase"

class GetVenuesUseCase @Inject constructor(
    private val repository: VenueRepository,
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase,
    private val checkLocationPermissionUseCase: CheckLocationPermissionUseCase
) {
    suspend fun execute(
        sort: String? = null,
        availableTime: String? = null,
        minPrice: Int? = null,
        maxPrice: Int? = null,
        infrastructure: Boolean? = null,
        district: String? = null
    ): Flow<List<Venue>> = flow {
        try {
            checkLocationPermissionUseCase.execute().collect { permissionState ->
                when (permissionState) {
                    LocationPermissionState.GRANTED -> {
                        Log.d(TAG, "Start fetching venues with permission")

                        getCurrentLocationUseCase.execute().collect { location ->
                            val venues = repository.getVenues(
                                latitude = location.latitude,
                                longitude = location.longitude,
                                sort = sort,
                                availableTime = availableTime,
                                minPrice = minPrice,
                                maxPrice = maxPrice,
                                infrastructure = infrastructure,
                                district = district,
                            )
                            emit(venues)
                        }
                    }
                    LocationPermissionState.DENIED -> {
                        Log.d(TAG, "Start fetching venues with NO permission")
                        val venues = repository.getVenues(
                            latitude = null,
                            longitude = null,
                            sort = sort,
                            availableTime = availableTime,
                            minPrice = minPrice,
                            maxPrice = maxPrice,
                            infrastructure = infrastructure,
                            district = district
                        )
                        emit(venues)
                    }
                }
            }
        } catch (e: Exception) {
            // Log the error if needed
            emit(emptyList())
        }
    }
}