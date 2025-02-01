package com.bron24.bron24_android.screens.menu_pages.map_page

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bron24.bron24_android.domain.entity.user.LocationPermissionState
import com.bron24.bron24_android.domain.usecases.location.CheckLocationPermissionUseCase
import com.bron24.bron24_android.domain.usecases.location.GetCurrentLocationUseCase
import com.bron24.bron24_android.domain.usecases.venue.GetVenueDetailsUseCase
import com.bron24.bron24_android.domain.usecases.venue.GetVenuesCoordinatesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class YandexMapPageVM @Inject constructor(
    private val getVenuesCoordinatesUseCase: GetVenuesCoordinatesUseCase,
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase,
    private val checkLocationPermissionUseCase: CheckLocationPermissionUseCase,
    private val getVenueDetailsUseCase: GetVenueDetailsUseCase
) : ViewModel(), YandexMapPageContract.ViewModel {

    override fun onDispatchers(intent: YandexMapPageContract.Intent): Job = intent {
        when (intent) {
            is YandexMapPageContract.Intent.ClickMarker -> {
                getVenueDetailsUseCase
                    .invoke(venueId = intent.location.venueId)
                    .onEach { venueDetails ->
                        if (intent.location.venueId != state.currentSelectedMarker?.venueId) reduce {
                            state.copy(
                                venueDetails = venueDetails.first,
                                currentSelectedMarker = intent.location,
                                imageUrls = venueDetails.second
                            )
                        }
                    }.launchIn(viewModelScope)

            }

            is YandexMapPageContract.Intent.CheckPermission -> {
                checkLocationPermissionUseCase.invoke().onEach { permissionState ->
                    reduce { state.copy(checkPermission = permissionState) }
                    if (permissionState == LocationPermissionState.GRANTED) {
                        initData()
                    } else {
                        postSideEffect("Permission Denied")
                    }
                }.launchIn(viewModelScope)
            }

            is YandexMapPageContract.Intent.DismissVenueDetails -> {
                reduce { state.copy(venueDetails = null) }
            }

            is YandexMapPageContract.Intent.ClickVenueBook -> {
                postSideEffect("Venue booking clicked: ${intent.venueDetails.venueName}")
            }

        }
    }

    override fun initData(): Job = intent {
        checkLocationPermissionUseCase.invoke().onEach {
            when (it) {
                LocationPermissionState.GRANTED -> {
                    getCurrentLocationUseCase.execute().onEach {
                        reduce { state.copy(userLocation = it) }
                    }.launchIn(viewModelScope)

                    getVenuesCoordinatesUseCase.invoke().onEach {
                        reduce { state.copy(venueCoordinates = it) }
                    }.launchIn(viewModelScope)
                }

                LocationPermissionState.DENIED -> {
                    postSideEffect("Error checking location permission denied!")
                }
            }
        }.launchIn(viewModelScope)
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