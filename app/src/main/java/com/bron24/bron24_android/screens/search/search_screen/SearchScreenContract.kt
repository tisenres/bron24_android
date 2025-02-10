package com.bron24.bron24_android.screens.search.search_screen

import com.bron24.bron24_android.common.VenueOrderInfo
import com.bron24.bron24_android.domain.entity.venue.Venue
import com.bron24.bron24_android.domain.entity.venue.VenueDetails
import com.bron24.bron24_android.screens.orderdetails.OrderDetailsContact.Intent
import kotlinx.coroutines.Job
import org.orbitmvi.orbit.ContainerHost

interface SearchScreenContract {
    interface ViewModel:ContainerHost<UIState, SideEffect>{
        fun onDispatchers(intent: Intent):Job
    }

    data class UIState(
        val isLoading:Boolean = false,
        val history:List<String> = emptyList(),
        val searchResult: List<Venue> = emptyList(),
        val imageUrls:List<String> = emptyList(),
        val venueDetails: VenueDetails? = null,
    )

    class SideEffect(val message:String)

    sealed interface Intent{
        data object Back:Intent
        data class ClickOrder(val info: VenueOrderInfo):Intent
        data class Search(val query:String):Intent
        data class OpenVenueDetails(val id:Int): Intent
    }

    interface Direction{
        suspend fun back()
        suspend fun moveToNext(info: VenueOrderInfo)
    }
}