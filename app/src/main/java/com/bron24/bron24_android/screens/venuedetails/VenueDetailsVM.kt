package com.bron24.bron24_android.screens.venuedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.viewModelScope
import com.bron24.bron24_android.domain.usecases.venue.GetVenueDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class VenueDetailsVM @Inject constructor(
    private val getVenueDetailsUseCase: GetVenueDetailsUseCase
): ViewModel(),VenueDetailsContract.ViewModel {
    override fun onDispatchers(intent: VenueDetailsContract.Intent): Job = intent {
        when(intent){
            VenueDetailsContract.Intent.ClickBack -> {}
            is VenueDetailsContract.Intent.ClickMap -> {}
        }
    }

    override fun initData(venueId:Int): Job = intent {
        getVenueDetailsUseCase.invoke(venueId)
            .onStart { reduce { state.copy(isLoading = true) } }
            .onEach { reduce { state.copy(isLoading = false, venue = it) }}
            .launchIn(viewModelScope)
    }

    override val container = container<VenueDetailsContract.UIState, VenueDetailsContract.SideEffect>(VenueDetailsContract.UIState())
}