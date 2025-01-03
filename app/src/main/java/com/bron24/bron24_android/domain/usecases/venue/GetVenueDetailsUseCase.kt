package com.bron24.bron24_android.domain.usecases.venue

import com.bron24.bron24_android.domain.entity.user.LocationPermissionState
import com.bron24.bron24_android.domain.entity.venue.VenueDetails
import com.bron24.bron24_android.domain.repository.VenueRepository
import com.bron24.bron24_android.domain.usecases.location.CheckLocationPermissionUseCase
import com.bron24.bron24_android.domain.usecases.location.GetCurrentLocationUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetVenueDetailsUseCase @Inject constructor(
    private val venueRepository: VenueRepository,
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase,
    private val checkLocationPermissionUseCase: CheckLocationPermissionUseCase
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(venueId: Int): Flow<VenueDetails> = flow {
        checkLocationPermissionUseCase.invoke()
            .flatMapLatest { permissionState ->
                when (permissionState) {
                    LocationPermissionState.GRANTED -> getCurrentLocationUseCase.execute()
                        .flatMapLatest { location ->
                            getVenueDetailsWithLocation(venueId, location.latitude, location.longitude)
                        }
                    LocationPermissionState.DENIED -> getVenueDetailsWithLocation(venueId, null, null)
                }
            }
            .catch { e ->
                // You can log the error here if needed
                throw e
            }
            .collect { venueDetails ->
                emit(venueDetails)
            }
    }

    private fun getVenueDetailsWithLocation(venueId: Int, latitude: Double?, longitude: Double?): Flow<VenueDetails> = flow {
        val venue = venueRepository.getVenueDetailsById(venueId, latitude, longitude)
        val pictures = venueRepository.getVenuePictures(venueId)
        val venueWithPictures = venue?.copy(imageUrls = pictures)
        if (venueWithPictures != null) {
            emit(venueWithPictures)
        } else {
            throw NoSuchElementException("Venue not found")
        }
    }
}