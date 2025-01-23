package com.bron24.bron24_android.screens.menu_pages.map_page

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bron24.bron24_android.domain.entity.user.LocationPermissionState
import com.bron24.bron24_android.domain.usecases.location.CheckLocationPermissionUseCase
import com.bron24.bron24_android.domain.usecases.location.GetCurrentLocationUseCase
import com.bron24.bron24_android.domain.usecases.venue.GetVenueDetailsUseCase
import com.bron24.bron24_android.domain.usecases.venue.GetVenuesCoordinatesUseCase
import com.bron24.bron24_android.screens.menu_pages.home_page.HomePageContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.Container
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