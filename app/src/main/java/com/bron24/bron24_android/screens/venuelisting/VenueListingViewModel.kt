package com.bron24.bron24_android.screens.venuelisting

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bron24.bron24_android.common.FilterOptions
import com.bron24.bron24_android.domain.entity.venue.Venue
import com.bron24.bron24_android.domain.usecases.venue.GetVenuesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject


private const val TAG = "VenueListingViewModel"

@HiltViewModel
class VenueListingViewModel @Inject constructor(
    private val getVenuesUseCase: GetVenuesUseCase
) : ViewModel() {
    private val _venues = MutableStateFlow<List<Venue>>(emptyList())
    val venues = _venues.asStateFlow()

    val isLoading = MutableSharedFlow<Boolean>()

    private val _sortOption = MutableStateFlow<SortOption?>(null)
    val sortOption = _sortOption.asStateFlow()

    private val _filterOptions = MutableSharedFlow<FilterOptions>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    init {
        Log.d(TAG, "Initializing VenueListingViewModel")
        Log.d("AAA", "init: ")
        viewModelScope.launch {
            _filterOptions.emit(FilterOptions())
        }
        getVenues()
    }

    private fun getVenues() {
        try {
            _filterOptions.onEach {filter->
                Log.d("SSS", "getVenues: $filter")
                getVenuesUseCase.execute(
                    sort = _sortOption.value?.name,
                    availableTime = filter.availableTime,
                    minPrice = filter.minPrice,
                    maxPrice = filter.maxPrice,
                    infrastructure = filter.infrastructure,
                    district = filter.district
                ).onStart {
                    isLoading.emit(true)
                }.onEach { venues ->
                    Log.d(TAG, "Received ${venues.size} venues")
                    Log.d("AAA", "Received ${venues} venues")
                    isLoading.emit(false)
                    _venues.value = venues
                }.launchIn(viewModelScope)
            }.launchIn(viewModelScope)

        } catch (e: Exception) {
            Log.e(TAG, "Error fetching venues", e)
            // You might want to handle the error here, e.g., show an error message to the user
        } finally {
            viewModelScope.launch {
                Log.d(TAG, "Finished fetching venues")
                isLoading.emit(false)
            }
        }

    }

    fun refreshVenues() {
        Log.d(TAG, "Refreshing venues")
        getVenues()
    }

    fun updateSortOption(sort: SortOption) {
        Log.d(TAG, "Updating sort option to: $sort")
        _sortOption.value = sort
        getVenues()
    }

    fun updateFilterOptions(filterOptions: FilterOptions) {
        Log.d("SSS", "updateFilterOptions: openUpdate")
        Log.d("SSS", "Updating filter options to: $filterOptions")
        viewModelScope.launch {
            _filterOptions.emit(filterOptions)
        }
        getVenues()
    }
}

//data class FilterOptions(
//    val availableTime: String? = null,
//    val minPrice: Int? = null,
//    val maxPrice: Int? = null,
//    val infrastructure: Boolean? = null,
//    val district: String? = null
//)

enum class SortOption {
    MIN_TO_MAX,
    MAX_TO_MIN,
    CLOSEST
}