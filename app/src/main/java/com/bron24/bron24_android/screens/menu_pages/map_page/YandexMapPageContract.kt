package com.bron24.bron24_android.screens.menu_pages.map_page

import androidx.compose.material.BottomSheetState
import com.bron24.bron24_android.common.VenueOrderInfo
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
        val isLoadingDetails: Boolean = false,
        val firstOpen:Int = 0,
        val showBottomSheet:Boolean = false,
        val checkPermission: LocationPermissionState = LocationPermissionState.DENIED,
        val userLocation: Location? = null,
        val imageUrls: List<String> = emptyList(),
        val currentSelectedMarker: VenueCoordinates? = null
    )

    data class SideEffect(
        val message: String
    )

    sealed interface Intent {
        data class ClickMarker(val location: VenueCoordinates) : Intent
        data class ClickVenueBook(val venueId: Int) : Intent
        data object DismissVenueDetails : Intent
        data object InitData: Intent
        data object DismissSheet:Intent
        data object RefreshLocation : Intent
        data class ClickOrder(val venueOrderInfo: VenueOrderInfo) : Intent
    }

    interface Direction {
        suspend fun back()
        suspend fun moveToBooking(info: VenueOrderInfo)
    }
}