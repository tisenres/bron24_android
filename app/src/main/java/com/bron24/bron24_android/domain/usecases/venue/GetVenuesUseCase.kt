package com.bron24.bron24_android.domain.usecases.venue

import com.bron24.bron24_android.domain.entity.enums.LocationPermissionState
import com.bron24.bron24_android.domain.entity.venue.Venue
import com.bron24.bron24_android.domain.repository.VenueRepository
import com.bron24.bron24_android.domain.usecases.location.CheckLocationPermissionUseCase
import com.bron24.bron24_android.domain.usecases.location.GetCurrentLocationUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

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
                        getCurrentLocationUseCase.execute().collect { location ->
                            val venues = repository.getVenues(
                                latitude = location.latitude,
                                longitude = location.longitude,
                                sort = sort,
                                availableTime = availableTime,
                                minPrice = minPrice,
                                maxPrice = maxPrice,
                                infrastructure = infrastructure,
                                district = district
                            )
                            emit(venues.map { venue ->
                                venue.copy(imageUrl = repository.getFirstVenuePicture(venue.venueId))
                            })
                        }
                    }
                    LocationPermissionState.DENIED -> {
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
                        emit(venues.map { venue ->
                            venue.copy(imageUrl = repository.getFirstVenuePicture(venue.venueId))
                        })
                    }
                }
            }
        } catch (e: Exception) {
            // Log the error if needed
            emit(emptyList())
        }
    }
}