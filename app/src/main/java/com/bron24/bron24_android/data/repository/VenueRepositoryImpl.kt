package com.bron24.bron24_android.data.repository

import com.bron24.bron24_android.data.network.apiservices.VenueApiService
import com.bron24.bron24_android.data.network.mappers.toDomainModel
import com.bron24.bron24_android.domain.entity.venue.Venue
import com.bron24.bron24_android.domain.entity.venue.VenueCoordinates
import com.bron24.bron24_android.domain.entity.venue.VenueDetails
import com.bron24.bron24_android.domain.repository.VenueRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

private const val TAG = "VenueRepositoryImpl"

class VenueRepositoryImpl @Inject constructor(
    private val apiService: VenueApiService
) : VenueRepository {

    override suspend fun getVenues(
        latitude: Double?,
        longitude: Double?,
        sort: String?,
        availableTime: String?,
        minPrice: Int?,
        maxPrice: Int?,
        infrastructure: Boolean?,
        district: String?
    ): List<Venue> = withContext(Dispatchers.IO) {
        try {
            Timber.tag(TAG).d("Start fetching venues")
            apiService.getVenues(
                latitude, longitude, sort, availableTime,
                minPrice, maxPrice, infrastructure, district
            )?.data?.map { it.toDomainModel() } ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getVenuesCoordinates(): List<VenueCoordinates> =
        withContext(Dispatchers.IO) {
            try {
                apiService.getVenuesCoordinates()?.map { it.toDomainModel() } ?: emptyList()
            } catch (e: Exception) {
                emptyList()
            }
        }

    override suspend fun getVenuePictures(venueId: Int): List<String> =
        withContext(Dispatchers.IO) {
            try {
                apiService.getVenuePictures(venueId)?.map { it.url } ?: emptyList()
            } catch (e: Exception) {
                emptyList()
            }
        }

    override suspend fun getVenueDetailsById(
        venueId: Int,
        latitude: Double?,
        longitude: Double?
    ): VenueDetails? = withContext(Dispatchers.IO) {
        try {
            apiService.getVenueDetails(venueId, latitude, longitude)?.data?.toDomainModel()
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun searchVenues(
        query: String?,
        latitude: Double?,
        longitude: Double?,
    ): List<Venue> = withContext(Dispatchers.IO) {
        try {
            apiService.searchVenues(query, latitude, longitude)
                ?.data?.map { it.toDomainModel() } ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

}