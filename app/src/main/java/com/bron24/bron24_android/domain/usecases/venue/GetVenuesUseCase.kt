package com.bron24.bron24_android.domain.usecases.venue

import com.bron24.bron24_android.domain.entity.venue.Venue
import com.bron24.bron24_android.domain.repository.VenueRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetVenuesUseCase @Inject constructor(
    private val repository: VenueRepository
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    fun execute(): Flow<List<Venue>> = repository.getVenues().flatMapLatest { venues ->
        combine(
            venues.map { venue ->
                repository.getVenuePicture(venue.venueId).map { imageUrl ->
                    venue.copy(imageUrl = imageUrl)
                }
            }
        ) { it.toList() }
    }

    suspend fun refresh() {
        repository.refreshVenues()
    }
}