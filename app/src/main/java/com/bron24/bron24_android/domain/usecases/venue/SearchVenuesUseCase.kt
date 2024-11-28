package com.bron24.bron24_android.domain.usecases.venue

import com.bron24.bron24_android.domain.entity.user.LocationPermissionState
import com.bron24.bron24_android.domain.entity.venue.Venue
import com.bron24.bron24_android.domain.repository.VenueRepository
import com.bron24.bron24_android.domain.usecases.location.CheckLocationPermissionUseCase
import com.bron24.bron24_android.domain.usecases.location.GetCurrentLocationUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchVenuesUseCase @Inject constructor(
    private val repository: VenueRepository,
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase,
    private val checkLocationPermissionUseCase: CheckLocationPermissionUseCase
) {
    suspend fun execute(query: String? = null): Flow<List<Venue>> = flow {
        try {
            checkLocationPermissionUseCase.execute().collect { permissionState ->
                when (permissionState) {
                    LocationPermissionState.GRANTED -> {
                        getCurrentLocationUseCase.execute().collect { location ->
                            val venues = repository.searchVenues(
                                query = query,
                                latitude = location.latitude,
                                longitude = location.longitude,
                            )
                            emit(venues)
                        }
                    }

                    LocationPermissionState.DENIED -> {
                        val venues = repository.searchVenues(
                            query = query,
                            latitude = null,
                            longitude = null
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