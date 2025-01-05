package com.bron24.bron24_android.screens.venuedetails

import com.bron24.bron24_android.domain.entity.order.Order
import com.bron24.bron24_android.domain.entity.user.Location
import com.bron24.bron24_android.domain.entity.venue.Venue
import com.bron24.bron24_android.domain.entity.venue.VenueDetails
import kotlinx.coroutines.Job
import org.orbitmvi.orbit.ContainerHost

interface VenueDetailsContract {
    interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onDispatchers(intent: Intent): Job
        fun initData(venueId: Int): Job
    }

    data class UIState(
        val isLoading: Boolean = false,
        val venue: VenueDetails? = null,
    )

    class SideEffect(val message: String)

    sealed interface Intent {
        data object ClickBack : Intent
        data class ClickMap(val location: Location):Intent
    }

    interface Direction {
        suspend fun back()
    }
}