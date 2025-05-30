package com.bron24.bron24_android.domain.repository

import com.bron24.bron24_android.common.FilterOptions
import com.bron24.bron24_android.domain.entity.favourite.Favourite
import com.bron24.bron24_android.domain.entity.offers.SpecialOffer
import com.bron24.bron24_android.domain.entity.venue.Venue
import com.bron24.bron24_android.domain.entity.venue.VenueCoordinates
import com.bron24.bron24_android.domain.entity.venue.VenueDetails
import kotlinx.coroutines.flow.Flow

interface VenueRepository {
    suspend fun getVenues(
        latitude: Double? = null,
        longitude: Double? = null,
    ): List<Venue>

    suspend fun getVenuesCoordinates(): List<VenueCoordinates>
    suspend fun getVenuePictures(venueId: Int): List<String>
    fun getVenueDetailsById(venueId: Int, latitude: Double?, longitude: Double?): Flow<VenueDetails>
    suspend fun searchVenues(query: String?, latitude: Double?, longitude: Double?): List<Venue>
    fun getAllFavourite(): Flow<List<Favourite>>
    suspend fun getVenueByFilter(latitude: Double?,longitude: Double?,filterOptions: FilterOptions):List<Venue>
    fun addFavourite(favourite: Favourite): Flow<Result<Unit>>
    fun deleteFavourite(id: Int): Flow<Result<Unit>>
    fun getSpecialOffers(): Flow<List<SpecialOffer>>
}
