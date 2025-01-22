package com.bron24.bron24_android.domain.usecases.venue

import com.bron24.bron24_android.domain.entity.venue.VenueCoordinates
import com.bron24.bron24_android.domain.repository.VenueRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetVenuesCoordinatesUseCase @Inject constructor(
    private val repository: VenueRepository
) {
    operator fun invoke(): Flow<List<VenueCoordinates>> = flow {
        emit(repository.getVenuesCoordinates())
    }
}