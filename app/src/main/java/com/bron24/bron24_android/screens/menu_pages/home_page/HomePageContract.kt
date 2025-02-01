package com.bron24.bron24_android.screens.menu_pages.home_page

import cafe.adriel.voyager.hilt.ScreenModelKey
import com.bron24.bron24_android.common.FilterOptions
import com.bron24.bron24_android.common.VenueOrderInfo
import com.bron24.bron24_android.domain.entity.offers.SpecialOffer
import com.bron24.bron24_android.domain.entity.user.Location
import com.bron24.bron24_android.domain.entity.venue.Venue
import com.bron24.bron24_android.domain.entity.venue.VenueDetails
import com.bron24.bron24_android.screens.util.AppViewModel
import com.bron24.bron24_android.screens.util.ScreenModelImpl
import com.bron24.bron24_android.screens.venuedetails.VenueDetailsContract.Intent
import kotlinx.coroutines.Job
import org.orbitmvi.orbit.ContainerHost

interface HomePageContract {
    @ScreenModelImpl(HomePageVM::class)
    interface ViewModel : AppViewModel<UIState,SideEffect> {
        fun onDispatchers(intent: Intent): Job
    }

    data class UIState(
        val isLoading: Boolean = true,
        val initial: Boolean = true,
        val refresh:Boolean = false,
        val firstName: String = "",
        val itemData: List<Venue> = emptyList(),
        val specialOffers: List<SpecialOffer> = emptyList(),
        val selectedSort: String? = null,
        val userLocation: Location = Location(33.33, 33.33),
        val venueDetails: VenueDetails? = null,
        val imageUrls: List<String> = emptyList()
    )

    class SideEffect(val message: String)

    sealed interface Intent {
        data object ClickSearch : Intent
        data object ClickFilter : Intent
        data class SelectedSort(val name: String) : Intent
        data object Refresh : Intent
        data class ClickItem(val venueId: Int) : Intent
        data class ClickOrder(val venueOrderInfo: VenueOrderInfo):Intent
    }

    interface Direction {
        suspend fun moveToSearch()
        suspend fun moveToFilter(block: (FilterOptions) -> Unit)
        suspend fun moveToDetails(venueId: Int)
        suspend fun openOrderScreen(info: VenueOrderInfo)
    }
}