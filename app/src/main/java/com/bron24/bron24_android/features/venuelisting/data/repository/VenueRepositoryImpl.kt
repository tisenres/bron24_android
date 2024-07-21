package com.bron24.bron24_android.features.venuelisting.data.repository

import com.bron24.bron24_android.features.venuelisting.data.mappers.toDomainModel
import com.bron24.bron24_android.features.venuelisting.data.remote.VenueApiService
import com.bron24.bron24_android.features.venuelisting.domain.entities.Venue
import com.bron24.bron24_android.features.venuelisting.domain.repository.VenueRepository
import javax.inject.Inject

class VenueRepositoryImpl @Inject constructor(
    private val apiService: VenueApiService
) : VenueRepository {
    override suspend fun getVenues(): List<Venue> {
        return apiService.getVenues().map { it.toDomainModel() }
    }
}

