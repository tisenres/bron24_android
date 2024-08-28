package com.bron24.bron24_android.data.repository

import com.bron24.bron24_android.data.network.mappers.toDomainModel
import com.bron24.bron24_android.data.network.apiservices.VenueApiService
import com.bron24.bron24_android.domain.entity.venue.Venue
import com.bron24.bron24_android.domain.entity.venue.VenueCoordinates
import com.bron24.bron24_android.domain.entity.venue.VenueDetails
import com.bron24.bron24_android.domain.repository.VenueRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class VenueRepositoryImpl @Inject constructor(
    private val apiService: VenueApiService
) : VenueRepository {

    override suspend fun getVenues(): List<Venue> = withContext(Dispatchers.IO) {
        apiService.getVenues().map { it.toDomainModel() }
    }

    override suspend fun getVenuesCoordinates(): List<VenueCoordinates> = withContext(Dispatchers.IO) {
        apiService.getVenuesCoordinates().map { it.toDomainModel() }
    }

    override suspend fun getFirstVenuePicture(venueId: Int): String? = withContext(Dispatchers.IO) {
        apiService.getVenuePictures(venueId).firstOrNull()?.url
    }

    override suspend fun getVenuePictures(venueId: Int): List<String> = withContext(Dispatchers.IO) {
        apiService.getVenuePictures(venueId).map { it.url }
    }

    override suspend fun getVenueDetailsById(venueId: Int): VenueDetails = withContext(Dispatchers.IO) {
        apiService.getVenueDetails(venueId).toDomainModel()
    }
}