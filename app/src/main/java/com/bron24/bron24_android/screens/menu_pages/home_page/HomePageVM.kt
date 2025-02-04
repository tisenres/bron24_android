package com.bron24.bron24_android.screens.menu_pages.home_page

import android.util.Log
import cafe.adriel.voyager.core.model.screenModelScope
import com.bron24.bron24_android.common.FilterOptions
import com.bron24.bron24_android.data.local.preference.LocalStorage
import com.bron24.bron24_android.domain.entity.venue.VenueDetails
import com.bron24.bron24_android.domain.usecases.location.GetCurrentLocationUseCase
import com.bron24.bron24_android.domain.usecases.offers.GetSpecialOfferUseCase
import com.bron24.bron24_android.domain.usecases.venue.GetFilterUseCase
import com.bron24.bron24_android.domain.usecases.venue.GetVenueDetailsUseCase
import com.bron24.bron24_android.domain.usecases.venue.GetVenuesUseCase
import com.google.android.play.integrity.internal.f
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
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
    private val getSpecialOfferUseCase: GetSpecialOfferUseCase,
    private val getVenueDetailsUseCase: GetVenueDetailsUseCase,
    private val getFilterUseCase: GetFilterUseCase
) : HomePageContract.ViewModel {
    init {
        localStorage.openMenu = true
    }

    override fun onDispatchers(intent: HomePageContract.Intent): Job = intent {
        when (intent) {
            HomePageContract.Intent.ClickSearch -> direction.moveToSearch()
            is HomePageContract.Intent.SelectedSort -> {
                reduce { state.copy(selectedSort = intent.name) }
            }

            HomePageContract.Intent.Refresh -> {
                if (state.count.isEmpty()) {
                    getVenuesUseCase.invoke().onStart {
                        reduce { state.copy(isLoading = true, refresh = true, checkBack = false) }
                    }.onEach {
                        it.onSuccess {
                            reduce { state.copy(isLoading = false, itemData = it, refresh = false) }
                        }.onFailure {
                            postSideEffect(it.message.toString())
                        }
                    }.onCompletion { reduce { state.copy(isLoading = false) } }.launchIn(screenModelScope)

                    getSpecialOfferUseCase.invoke().onEach {
                        reduce { state.copy(specialOffers = it) }
                    }.launchIn(screenModelScope)
                } else {
                    getFilterUseCase.invoke(state.filterOptions ?: FilterOptions()).onStart {
                        reduce { state.copy(isLoading = true, refresh = true) }
                    }.onEach {
                        reduce { state.copy(itemData = it) }
                    }.onCompletion { reduce { state.copy(isLoading = false, refresh = false) } }.launchIn(screenModelScope)
                }
            }

            is HomePageContract.Intent.ClickItem -> {
                getVenueDetailsUseCase.invoke(intent.venueId)
                    .onStart { reduce { state.copy(isLoading = true) } }
                    .onEach {
                        reduce { state.copy(isLoading = false, venueDetails = it.first, imageUrls = it.second) }
                    }.launchIn(screenModelScope)
            }

            is HomePageContract.Intent.ClickOrder -> {
                direction.openOrderScreen(intent.venueOrderInfo)
            }

            is HomePageContract.Intent.FilterIntent -> {
                reduce {
                    state.copy(
                        filter = state.filter.copy(
                            selParking = intent.filter.selParking,
                            selRoom = intent.filter.selRoom,
                            selShower = intent.filter.selShower,
                            selOutdoor = intent.filter.selOutdoor,
                            selIndoor = intent.filter.selIndoor,
                            rangeTime = intent.filter.rangeTime,
                            rangeSumma = intent.filter.rangeSumma,
                            startTime = intent.filter.startTime,
                            endTime = intent.filter.endTime,
                            minSumma = intent.filter.minSumma,
                            maxSumma = intent.filter.maxSumma,
                            location = intent.filter.location,
                            selectLocation = intent.filter.selectLocation,
                            selectedDate = intent.filter.selectedDate
                        )
                    )
                }
            }

            is HomePageContract.Intent.Filter -> {
                var count = 0
                if (intent.options.selShower || intent.options.selParking || intent.options.selRoom) {
                    count++
                }
                if (intent.options.maxSumma < 1000000 || intent.options.minSumma > 0) {
                    count++
                }
                if (intent.options.selIndoor || intent.options.selOutdoor) {
                    count++
                }
                if (intent.options.startTime != "00:00" || intent.options.endTime != "23:59") {
                    count++
                }
                if (intent.options.selectedDate.isNotEmpty()) {
                    count++
                }
                if (intent.options.location.isNotEmpty()) {
                    count++
                }
                getSpecialOfferUseCase.invoke().onEach {
                    reduce { state.copy(specialOffers = it) }
                }.launchIn(screenModelScope)
                if (count != 0) {
                    reduce { state.copy(count = count.toString(), filterOptions = intent.options, checkBack = true, isLoading = true) }
                    getFilterUseCase.invoke(intent.options).onEach {
                        reduce { state.copy(itemData = it, isLoading = false) }
                    }.onCompletion {reduce { state.copy(isLoading = false) } }.launchIn(screenModelScope)
                } else {
                    reduce { state.copy(filterOptions = FilterOptions(), checkBack = false, count = "") }
                    result()
                }
            }

            HomePageContract.Intent.ClickReset -> {
                reduce { state.copy(count = "", filter = HomePageContract.FilterUiState()) }
            }
        }
    }

    private fun result() = intent {
        getVenuesUseCase.invoke().onEach {
            it.onSuccess {
                reduce { state.copy(isLoading = false, itemData = it) }
            }.onFailure {
                postSideEffect(it.message.toString())
            }
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
            intent {
                reduce { state.copy(initial = false) }
                getVenuesUseCase.invoke().onEach {
                    it.onSuccess {
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