package com.bron24.bron24_android.domain.usecases.venue

import com.bron24.bron24_android.domain.repository.VenueRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteFavouriteUseCase @Inject constructor(
    private val venueRepository: VenueRepository
) {
    operator fun invoke(id: Int): Flow<Result<Unit>> = venueRepository.deleteFavourite(id)
}