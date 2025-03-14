package com.bron24.bron24_android.domain.usecases.venue

import com.bron24.bron24_android.domain.entity.user.LocationPermissionState
import com.bron24.bron24_android.domain.entity.venue.Venue
import com.bron24.bron24_android.domain.repository.VenueRepository
import com.bron24.bron24_android.domain.usecases.location.CheckLocationPermissionUseCase
import com.bron24.bron24_android.domain.usecases.location.GetCurrentLocationUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetVenuesUseCase @Inject constructor(
    private val repository: VenueRepository,
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase,
    private val checkLocationPermissionUseCase: CheckLocationPermissionUseCase
) {
    operator fun invoke(): Flow<Result<List<Venue>>> = flow {
        checkLocationPermissionUseCase.invoke().collect { permissionState ->
            try {
                when (permissionState) {
                    LocationPermissionState.GRANTED -> {
                        getCurrentLocationUseCase.execute().collect { location ->
                            val venues = repository.getVenues(
                                latitude = location.latitude,
                                longitude = location.longitude,
                            )
                            emit(Result.success(venues))
                        }
                    }

                    LocationPermissionState.DENIED -> {
                        val venues = repository.getVenues(
                            latitude = null,
                            longitude = null,
                        )
                        emit(Result.success(venues))
                    }
                }
            }catch (e:Exception){

            }
        }
    }.catch { emit(Result.failure(it)) }.flowOn(Dispatchers.IO)
}