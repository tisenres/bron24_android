package com.bron24.bron24_android.domain.usecases.venue

import com.bron24.bron24_android.domain.entity.favourite.Favourite
import com.bron24.bron24_android.domain.repository.VenueRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddFavouriteUseCase @Inject constructor(
    private val venueRepository: VenueRepository
) {
    operator fun invoke(favourite: Favourite): Flow<Result<Unit>> =
        venueRepository.addFavourite(favourite)
}