package com.bron24.bron24_android.screens.menu_pages.home_page

import android.util.Log
import androidx.compose.ui.util.trace
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.core.model.screenModelScope
import com.bron24.bron24_android.common.FilterOptions
import com.bron24.bron24_android.data.local.preference.LocalStorage
import com.bron24.bron24_android.domain.usecases.location.GetCurrentLocationUseCase
import com.bron24.bron24_android.domain.usecases.offers.GetSpecialOfferUseCase
import com.bron24.bron24_android.domain.usecases.venue.GetVenuesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject
import javax.inject.Singleton

class HomePageVM @Inject constructor(
    private val direction: HomePageContract.Direction,
    private val localStorage: LocalStorage,
    private val getVenuesUseCase: GetVenuesUseCase,
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase,
    private val getSpecialOfferUseCase: GetSpecialOfferUseCase
) : HomePageContract.ViewModel {
    private var filterOptions: FilterOptions? = null

    init {
        localStorage.openMenu = true
    }

    override fun onDispatchers(intent: HomePageContract.Intent): Job = intent {
        when (intent) {
            HomePageContract.Intent.ClickFilter -> direction.moveToFilter {
                filterOptions = it
            }

            HomePageContract.Intent.ClickSearch -> direction.moveToSearch()
            is HomePageContract.Intent.SelectedSort -> {
                reduce { state.copy(selectedSort = intent.name) }
            }

            HomePageContract.Intent.Refresh -> {
                getVenuesUseCase.invoke().onStart {
                    reduce { state.copy(isLoading = true, refresh = true) }
                }.onEach {
                    it.onSuccess {
                        reduce { state.copy(isLoading = false, itemData = it, refresh = false) }
                    }.onFailure {
                        postSideEffect(it.message.toString())
                    }
                }.launchIn(screenModelScope)

                getSpecialOfferUseCase.invoke().onEach {
                    reduce { state.copy(specialOffers = it) }
                }.launchIn(screenModelScope)
            }

            is HomePageContract.Intent.ClickItem -> {
                direction.moveToDetails(intent.venueId)
            }
        }
    }

    private fun filterResult() = intent {
        getVenuesUseCase.invoke(state.selectedSort, filterOptions).onEach {
            it.onSuccess {
                reduce { state.copy(isLoading = false, itemData = it) }
            }.onFailure {
                postSideEffect(it.message.toString())
            }
        }.launchIn(screenModelScope)
        getCurrentLocationUseCase.execute().onEach {
            reduce { state.copy(userLocation = it) }
        }.launchIn(screenModelScope)
    }

    private fun postSideEffect(message: String) {
        intent {
            postSideEffect(HomePageContract.SideEffect(message))
        }
    }

    override val container =
        container<HomePageContract.UIState, HomePageContract.SideEffect>(
            initialState = HomePageContract.UIState(
                firstName = localStorage.firstName
            )
        ) {
            Log.d("AAA","onCreate")
            intent {
                reduce { state.copy(initial = false) }
                getVenuesUseCase.invoke(state.selectedSort, filterOptions).onEach {
                    it.onSuccess {
                        if (filterOptions != null) {
                            filterResult()
                        }
                        reduce { state.copy(isLoading = false, itemData = it) }
                    }.onFailure {
                        postSideEffect(it.message.toString())
                    }
                }.launchIn(screenModelScope)

                getCurrentLocationUseCase.execute().onEach {
                    reduce { state.copy(userLocation = it) }
                }.launchIn(screenModelScope)

                getSpecialOfferUseCase.invoke().onEach {
                    reduce { state.copy(specialOffers = it) }
                }.launchIn(screenModelScope)
            }
        }
}