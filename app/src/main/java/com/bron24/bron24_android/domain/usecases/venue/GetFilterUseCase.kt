package com.bron24.bron24_android.domain.usecases.venue

import com.bron24.bron24_android.common.FilterOptions
import com.bron24.bron24_android.domain.entity.user.LocationPermissionState
import com.bron24.bron24_android.domain.entity.venue.Venue
import com.bron24.bron24_android.domain.repository.VenueRepository
import com.bron24.bron24_android.domain.usecases.location.CheckLocationPermissionUseCase
import com.bron24.bron24_android.domain.usecases.location.GetCurrentLocationUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFilterUseCase @Inject constructor(
    private val venueRepository: VenueRepository,
    private val currentLocationUseCase: GetCurrentLocationUseCase,
    private val checkLocationPermissionUseCase: CheckLocationPermissionUseCase
){
    operator fun invoke(filterOptions: FilterOptions):Flow<List<Venue>> = flow {
        checkLocationPermissionUseCase.invoke().collect { permissionState ->
            try {
                when (permissionState) {
                    LocationPermissionState.GRANTED -> {
                        currentLocationUseCase.execute().collect { location ->
                            val venues = venueRepository.getVenueByFilter(
                                latitude = location.latitude,
                                longitude = location.longitude,
                                filterOptions = filterOptions
                            )
                            emit(venues)
                        }
                    }

                    LocationPermissionState.DENIED -> {
                        val venues = venueRepository.getVenueByFilter(
                            latitude = null,
                            longitude = null,
                            filterOptions
                        )
                        emit(venues)
                    }
                }
            }catch (e:Exception){
                emit(emptyList())
            }
        }
    }
}