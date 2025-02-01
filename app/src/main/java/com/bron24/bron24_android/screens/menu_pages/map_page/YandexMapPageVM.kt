package com.bron24.bron24_android.screens.menu_pages.map_page

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bron24.bron24_android.domain.entity.user.Location
import com.bron24.bron24_android.domain.entity.user.LocationPermissionState
import com.bron24.bron24_android.domain.usecases.location.CheckLocationPermissionUseCase
import com.bron24.bron24_android.domain.usecases.location.GetCurrentLocationUseCase
import com.bron24.bron24_android.domain.usecases.venue.GetVenueDetailsUseCase
import com.bron24.bron24_android.domain.usecases.venue.GetVenuesCoordinatesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class YandexMapPageVM @Inject constructor(
    private val getVenuesCoordinatesUseCase: GetVenuesCoordinatesUseCase,
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase,
    private val checkLocationPermissionUseCase: CheckLocationPermissionUseCase,
    private val getVenueDetailsUseCase: GetVenueDetailsUseCase
) : ViewModel(), YandexMapPageContract.ViewModel {

    private val defaultLocation = Location(41.311198, 69.279746)

    override fun onDispatchers(intent: YandexMapPageContract.Intent): Job = intent {
        when (intent) {
            is YandexMapPageContract.Intent.ClickMarker -> {
                getVenueDetailsUseCase
                    .invoke(venueId = intent.location.venueId)
                    .onStart {
                        reduce {
                            state.copy(isLoading = true)
                        }
                    }
                    .onEach { venueDetails ->
                        reduce {
                            state.copy(
                                firstOpen = 1,
                                venueCoordinates = state.venueCoordinates.map {
                                    if (it.venueId != intent.location.venueId) {
                                        it.copy(selected = true)
                                    } else it.copy(
                                        selected = false
                                    )
                                },
                                venueDetails = venueDetails.first,
                                currentSelectedMarker = intent.location,
                                imageUrls = venueDetails.second,
                                isLoading = false
                            )
                        }
                    }
                    .launchIn(viewModelScope)

            }

            is YandexMapPageContract.Intent.InitData -> {
                initData()
//                checkLocationPermissionUseCase.invoke().onEach { permissionState ->
//                    reduce { state.copy(checkPermission = permissionState) }
//                    initData()
//                    if (permissionState == LocationPermissionState.GRANTED) {
//                        initData()
//                    } else {
//                        postSideEffect("Permission Denied")
//                    }
//                }.launchIn(viewModelScope)
            }

            is YandexMapPageContract.Intent.DismissVenueDetails -> {
                reduce { state.copy(venueDetails = null) }
            }

            is YandexMapPageContract.Intent.ClickVenueBook -> {
                postSideEffect("Venue booking clicked: ${intent.venueDetails.venueName}")
            }

        }
    }

//    override fun initData(): Job = intent {
//        checkLocationPermissionUseCase.invoke().onEach {
//            when (it) {
//                LocationPermissionState.GRANTED -> {
//                    getCurrentLocationUseCase.execute()
//                        .onStart {
//                            reduce { state.copy(isLoading = true) }
//                        }.onEach {
//                            reduce { state.copy(userLocation = it, isLoading = false) }
//                        }.launchIn(viewModelScope)
//
//                    getVenuesCoordinatesUseCase.invoke().onStart {
//                        reduce { state.copy(isLoading = true) }
//                    }.onEach {
//                        reduce { state.copy(venueCoordinates = it, isLoading = false) }
//                    }.launchIn(viewModelScope)
//                }
//
//                LocationPermissionState.DENIED -> {
//                    postSideEffect("Error checking location permission denied!")
//                }
//            }
//        }.launchIn(viewModelScope)
//    }

    override fun initData(): Job = intent {
        checkLocationPermissionUseCase.invoke().onEach { permission ->
            when (permission) {
                LocationPermissionState.GRANTED -> {
                    combine(
                        getCurrentLocationUseCase.execute(),
                        getVenuesCoordinatesUseCase.invoke()
                    ) { currentLocation, venuesCoordinates ->
                        currentLocation to venuesCoordinates
                    }
                        .onStart { reduce { state.copy(isLoading = true) } }
                        .onEach { (currentLocation, venuesCoordinates) ->
                            reduce {
                                state.copy(
                                    userLocation = currentLocation,
                                    venueCoordinates = venuesCoordinates,
                                    isLoading = false,
                                    checkPermission = permission
                                )
                            }
                        }
                        .launchIn(viewModelScope)
                }

                LocationPermissionState.DENIED -> {
                    getVenuesCoordinatesUseCase.invoke()
                        .onStart { reduce { state.copy(isLoading = true) } }
                        .onEach { venuesCoordinates ->
                            reduce {
                                state.copy(
                                    userLocation = defaultLocation,
                                    venueCoordinates = venuesCoordinates,
                                    isLoading = false,
                                    checkPermission = permission
                                )
                            }
                            postSideEffect("Permission Denied")
                        }
                        .launchIn(viewModelScope)
                }
            }
        }
            .launchIn(viewModelScope)
    }

    private fun postSideEffect(message: String) {
        intent {
            postSideEffect(YandexMapPageContract.SideEffect(message))
        }
    }

    override val container =
        container<YandexMapPageContract.UIState, YandexMapPageContract.SideEffect>(
            YandexMapPageContract.UIState()
        )
}