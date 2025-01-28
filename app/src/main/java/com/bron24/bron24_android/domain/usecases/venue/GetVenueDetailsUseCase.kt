package com.bron24.bron24_android.domain.usecases.venue

import com.bron24.bron24_android.domain.entity.user.LocationPermissionState
import com.bron24.bron24_android.domain.entity.venue.VenueDetails
import com.bron24.bron24_android.domain.repository.OrdersRepository
import com.bron24.bron24_android.domain.repository.VenueRepository
import com.bron24.bron24_android.domain.usecases.location.CheckLocationPermissionUseCase
import com.bron24.bron24_android.domain.usecases.location.GetCurrentLocationUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetVenueDetailsUseCase @Inject constructor(
    private val venueRepository: VenueRepository,
    private val ordersRepository: OrdersRepository,
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase,
    private val checkLocationPermissionUseCase: CheckLocationPermissionUseCase,
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(venueId: Int): Flow<Pair<VenueDetails, List<String>>> = flow {
        checkLocationPermissionUseCase.invoke().collect {
            when (it) {
                LocationPermissionState.GRANTED -> {
                    getCurrentLocationUseCase.execute().collect {
                        val venueDetails = venueRepository.getVenueDetailsById(venueId, longitude = it.longitude, latitude = it.latitude)
                        emitAll(
                            venueDetails.flatMapConcat { data ->
                                flow {
                                    val images = venueRepository.getVenuePictures(venueId)
                                    emit(Pair(first = data, second = images))
                                }
                            }
                        )
                    }
                }

                LocationPermissionState.DENIED -> {
                    val venueDetails = venueRepository.getVenueDetailsById(venueId, longitude = null, latitude = null)
                    emitAll(
                        venueDetails.flatMapConcat { data ->
                            flow {
                                val images = venueRepository.getVenuePictures(venueId)
                                emit(Pair(first = data, second = images))
                            }
                        }
                    )
                }
            }
        }
    }
}
//        checkLocationPermissionUseCase.invoke().flatMapLatest {
//            when (it) {
////                LocationPermissionState.GRANTED -> {
////                    getCurrentLocationUseCase.execute().flatMapLatest {location->
////                        val orderData = venueRepository.getVenueDetailsById(venueId, latitude =location )
////
////                    }
////
////                }
////                LocationPermissionState.DENIED -> {
////
////                }
//            }
//        }
//    }.catch {  }.collect{
//
//    }


//        checkLocationPermissionUseCase.invoke()
//            .flatMapLatest { permissionState ->
//                when (permissionState) {
//                    LocationPermissionState.GRANTED -> getCurrentLocationUseCase.execute()
//                        .flatMapLatest { location ->
//                            getVenueDetailsWithLocation(venueId, location.latitude, location.longitude)
//                        }
//                    LocationPermissionState.DENIED -> getVenueDetailsWithLocation(venueId, null, null)
//                }
//            }
//            .catch { e ->
//                // You can log the error here if needed
//                throw e
//            }
//            .collect { venueDetails ->
//                emit(venueDetails)
//            }

//    private fun getVenueDetailsWithLocation(venueId: Int, latitude: Double?, longitude: Double?): Flow<VenueDetails> = flow {
//        val venue = venueRepository.getVenueDetailsById(venueId, latitude, longitude)
//        val pictures = venueRepository.getVenuePictures(venueId)
//        val venueWithPictures = venue?.copy(imageUrl = pictures)
//        if (venueWithPictures != null) {
//            emit(venueWithPictures)
//        }
//    }

