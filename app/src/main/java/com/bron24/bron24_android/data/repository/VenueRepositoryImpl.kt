package com.bron24.bron24_android.data.repository

import com.bron24.bron24_android.data.local.db.VenueDao
import com.bron24.bron24_android.data.local.db.VenuePictureEntity
import com.bron24.bron24_android.data.network.mappers.toDomainModel
import com.bron24.bron24_android.data.network.apiservices.VenueApiService
import com.bron24.bron24_android.data.network.mappers.toAddressEntity
import com.bron24.bron24_android.data.network.mappers.toVenueEntity
import com.bron24.bron24_android.domain.entity.venue.Venue
import com.bron24.bron24_android.domain.entity.venue.VenueCoordinates
import com.bron24.bron24_android.domain.entity.venue.VenueDetails
import com.bron24.bron24_android.domain.repository.VenueRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEmpty
import javax.inject.Inject

class VenueRepositoryImpl @Inject constructor(
    private val apiService: VenueApiService,
    private val venueDao: VenueDao
) : VenueRepository {

    override fun getVenues(): Flow<List<Venue>> {
        return venueDao.getVenues().map { venuesWithAddresses ->
            venuesWithAddresses.map { venueWithAddress ->
                Venue(
                    venueId = venueWithAddress.venue.venueId,
                    venueName = venueWithAddress.venue.venueName,
                    pricePerHour = venueWithAddress.venue.pricePerHour,
                    address = venueWithAddress.address.toDomainModel(),
                    imageUrl = null // We'll fetch this separately
                )
            }
        }.onEmpty {
            emit(fetchAndCacheVenues())
        }
    }

    private suspend fun fetchAndCacheVenues(): List<Venue> {
        val venues = apiService.getVenues()
        venueDao.insertVenues(venues.map { it.toVenueEntity() })
        venueDao.insertAddresses(venues.map { it.address.toAddressEntity() })
        return venues.map { it.toDomainModel() }
    }

    override suspend fun refreshVenues() {
        fetchAndCacheVenues()
    }

    override suspend fun getVenuesCoordinates(): List<VenueCoordinates> {
        return apiService.getVenuesCoordinates().map { it.toDomainModel() }
    }

    override fun getVenuePicture(venueId: Int): Flow<String?> {
        return venueDao.getFirstVenuePicture(venueId)
            .map { it?.imageUrl }
            .onEmpty {
                emit(fetchAndCacheFirstVenuePicture(venueId))
            }
    }

    private suspend fun fetchAndCacheFirstVenuePicture(venueId: Int): String? {
        val pictures = apiService.getVenuePictures(venueId)
        val firstPicture = pictures.firstOrNull()
        if (firstPicture != null) {
            venueDao.insertVenuePicture(VenuePictureEntity(venueId = venueId, imageUrl = firstPicture.url))
            return firstPicture.url
        }
        return null
    }

    override suspend fun getVenueDetailsById(venueId: Int): VenueDetails {
        return apiService.getVenueDetails(venueId).toDomainModel()
    }
}