package com.bron24.bron24_android.domain.usecases.offers

import com.bron24.bron24_android.domain.entity.offers.SpecialOffer
import com.bron24.bron24_android.domain.repository.VenueRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSpecialOfferUseCase @Inject constructor(
    private val venueRepository: VenueRepository
) {
    operator fun invoke(): Flow<List<SpecialOffer>> =
        venueRepository.getSpecialOffers()
}