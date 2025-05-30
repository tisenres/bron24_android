package com.bron24.bron24_android.screens.search.search_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bron24.bron24_android.domain.usecases.venue.GetVenueDetailsUseCase
import com.bron24.bron24_android.domain.usecases.venue.SearchVenuesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class SearchScreenVM @Inject constructor(
    private val searchVenuesUseCase: SearchVenuesUseCase,
    private val getVenueDetailsUseCase: GetVenueDetailsUseCase,
    private val direction: SearchScreenContract.Direction
) : ViewModel(), SearchScreenContract.ViewModel {
    override fun onDispatchers(intent: SearchScreenContract.Intent): Job = intent {
        when (intent) {
            SearchScreenContract.Intent.Back -> {
                direction.back()
            }

            is SearchScreenContract.Intent.Search -> {
                searchVenuesUseCase.invoke(intent.query).onStart {
                    reduce { state.copy(isLoading = true) }
                }.onEach {
                    reduce { state.copy(searchResult = it, isLoading = false) }
                }.onCompletion {
                    reduce { state.copy(isLoading = false) }
                }.launchIn(viewModelScope)
            }

            is SearchScreenContract.Intent.OpenVenueDetails -> {
                getVenueDetailsUseCase.invoke(intent.id)
                    .onStart { reduce { state.copy(isLoading = true) } }
                    .onEach {
                        reduce { state.copy(isLoading = false, venueDetails = it.first, imageUrls = it.second) }
                    }.launchIn(viewModelScope)
            }

            is SearchScreenContract.Intent.ClickOrder -> {
                direction.moveToNext(intent.info)
            }
        }
    }

    override val container = container<SearchScreenContract.UIState, SearchScreenContract.SideEffect>(SearchScreenContract.UIState())

}