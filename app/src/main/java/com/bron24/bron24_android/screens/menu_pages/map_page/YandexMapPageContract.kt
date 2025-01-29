package com.bron24.bron24_android.screens.menu_pages.map_page

import com.bron24.bron24_android.domain.entity.user.Location
import com.bron24.bron24_android.domain.entity.user.LocationPermissionState
import com.bron24.bron24_android.domain.entity.venue.VenueCoordinates
import com.bron24.bron24_android.domain.entity.venue.VenueDetails
import kotlinx.coroutines.Job
import org.orbitmvi.orbit.ContainerHost

interface YandexMapPageContract {
    interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onDispatchers(intent: Intent): Job
        fun initData(): Job
    }

    data class UIState(
        val venueCoordinates: List<VenueCoordinates> = emptyList(),
        val venueDetails: VenueDetails? = null,
        val isLoading: Boolean = false,
        val checkPermission: LocationPermissionState = LocationPermissionState.DENIED,
        val userLocation: Location = Location(42.23, 43.33),
        val imageUrls: List<String> = emptyList()
    )

    data class SideEffect(
        val message: String
    )

    sealed interface Intent {
        data class ClickMarker(val location: VenueCoordinates) : Intent
        data class ClickVenueBook(val venueDetails: VenueDetails) : Intent
        data object CheckPermission : Intent
    }

    interface Direction {
        suspend fun back()
        suspend fun moveToInfo(venueDetails: VenueDetails)
    }
}