package com.bron24.bron24_android.screens.venuelisting

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bron24.bron24_android.domain.entity.venue.Venue
import com.bron24.bron24_android.domain.usecases.venue.GetVenuesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _sortOption = MutableStateFlow<SortOption?>(null)
    val sortOption = _sortOption.asStateFlow()

    private val _filterOptions = MutableStateFlow(FilterOptions())
    val filterOptions = _filterOptions.asStateFlow()

    init {
        Log.d(TAG, "Initializing VenueListingViewModel")
        getVenues()
    }

    private fun getVenues() {
        viewModelScope.launch {
            Log.d(TAG, "Starting to fetch venues")
            _isLoading.value = true
            try {
                Log.d(TAG, "Executing GetVenuesUseCase with sort: ${_sortOption.value?.name}, filters: $_filterOptions")
                getVenuesUseCase.execute(
                    sort = _sortOption.value?.name,
                    availableTime = _filterOptions.value.availableTime,
                    minPrice = _filterOptions.value.minPrice,
                    maxPrice = _filterOptions.value.maxPrice,
                    infrastructure = _filterOptions.value.infrastructure,
                    district = _filterOptions.value.district
                ).collect { venues ->
                    Log.d(TAG, "Received ${venues.size} venues")
                    _isLoading.value = false
                    _venues.value = venues
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching venues", e)
                // You might want to handle the error here, e.g., show an error message to the user
            } finally {
                Log.d(TAG, "Finished fetching venues")
                _isLoading.value = false
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
        Log.d(TAG, "Updating filter options to: $filterOptions")
        _filterOptions.value = filterOptions
        getVenues()
    }
}

data class FilterOptions(
    val availableTime: String? = null,
    val minPrice: Int? = null,
    val maxPrice: Int? = null,
    val infrastructure: Boolean? = null,
    val district: String? = null
)

enum class SortOption {
    MIN_TO_MAX,
    MAX_TO_MIN,
    CLOSEST
}