package com.bron24.bron24_android.domain.usecases.venue

import com.bron24.bron24_android.domain.entity.favourite.Favourite
import com.bron24.bron24_android.domain.repository.VenueRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllFavouriteUseCase @Inject constructor(
    private val venueRepository: VenueRepository
) {
    operator fun invoke():Flow<List<Favourite>> = venueRepository.getAllFavourite()
}