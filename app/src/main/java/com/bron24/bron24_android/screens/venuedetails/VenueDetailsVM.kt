package com.bron24.bron24_android.screens.venuedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.core.model.screenModelScope
import com.bron24.bron24_android.domain.usecases.venue.GetVenueDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

class VenueDetailsVM @Inject constructor(
    private val getVenueDetailsUseCase: GetVenueDetailsUseCase,
    private val direction: VenueDetailsContract.Direction
):VenueDetailsContract.ViewModel {
    override fun onDispatchers(intent: VenueDetailsContract.Intent): Job = intent {
        when(intent){
            VenueDetailsContract.Intent.ClickBack -> {
                direction.back()
            }
            is VenueDetailsContract.Intent.ClickMap -> {}
            is VenueDetailsContract.Intent.ClickOrder -> {
                direction.moveToNext(intent.venueOrderInfo)
            }
        }
    }

    override fun initData(venueId: Int): Job = intent {

    }

    override val container = container<VenueDetailsContract.UIState, VenueDetailsContract.SideEffect>(VenueDetailsContract.UIState()){}
}