package com.bron24.bron24_android.screens.venuelisting

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
        viewModelScope.launch {
            _filterOptions.emit(FilterOptions())
        }
        getVenues()
    }

    private fun getVenues() {

    }

    fun refreshVenues() {
        getVenues()
    }

    fun updateSortOption(sort: SortOption) {
        _sortOption.value = sort
        getVenues()
    }

    fun updateFilterOptions(filterOptions: FilterOptions) {
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