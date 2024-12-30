package com.bron24.bron24_android.domain.usecases.venue

import android.util.Log
import com.bron24.bron24_android.common.FilterOptions
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

private const val TAG = "GetVenuesUseCase"

class GetVenuesUseCase @Inject constructor(
    private val repository: VenueRepository,
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase,
    private val checkLocationPermissionUseCase: CheckLocationPermissionUseCase
) {
    operator fun invoke(
        sort: String? = null,
        filterOptions: FilterOptions? = null
    ): Flow<Result<List<Venue>>> = flow {
        try {
            checkLocationPermissionUseCase.invoke().collect { permissionState ->
                when (permissionState) {
                    LocationPermissionState.GRANTED -> {
                        getCurrentLocationUseCase.execute().collect { location ->
                            val venues = repository.getVenues(
                                latitude = location.latitude,
                                longitude = location.longitude,
                                sort = sort,
                                availableTime = filterOptions?.availableTime,
                                minPrice = filterOptions?.minPrice,
                                maxPrice = filterOptions?.maxPrice,
                                infrastructure = filterOptions?.infrastructure,
                                district = filterOptions?.district,
                            )
                            emit(Result.success(venues))
                        }
                    }
                    LocationPermissionState.DENIED -> {
                        val venues = repository.getVenues(
                            latitude = null,
                            longitude = null,
                            sort = sort,
                            availableTime = filterOptions?.availableTime,
                            minPrice = filterOptions?.minPrice,
                            maxPrice = filterOptions?.maxPrice,
                            infrastructure = filterOptions?.infrastructure,
                            district = filterOptions?.district
                        )
                        emit(Result.success(venues))
                    }
                }
            }
        } catch (e: Exception) {
            // Log the error if needed
            emit(Result.success(emptyList()))
        }
    }.catch { emit(Result.failure(it)) }.flowOn(Dispatchers.IO)
}