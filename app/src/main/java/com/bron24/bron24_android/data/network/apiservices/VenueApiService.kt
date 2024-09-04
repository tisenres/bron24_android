package com.bron24.bron24_android.data.network.apiservices

import com.bron24.bron24_android.data.network.dto.venue.PictureDto
import com.bron24.bron24_android.data.network.dto.venue.VenueCoordinatesDto
import com.bron24.bron24_android.data.network.dto.venue.VenueDetailsDto
import com.bron24.bron24_android.data.network.dto.venue.VenueDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface VenueApiService {
    @GET("api/v1/venues")
    suspend fun getVenues(
        @Query("lat") latitude: Double? = null,
        @Query("lon") longitude: Double? = null,
        @Query("sort") sort: String? = null,
        @Query("available_time") availableTime: String? = null,
        @Query("min_price") minPrice: Int? = null,
        @Query("max_price") maxPrice: Int? = null,
        @Query("infrastructure") infrastructure: Boolean? = null,
        @Query("district") district: String? = null
    ): List<VenueDto>

    @GET("api/v1/venues/coordinates/")
    suspend fun getVenuesCoordinates(): List<VenueCoordinatesDto>

    @GET("api/v1/venues/pictures/{id}/")
    suspend fun getVenuePictures(@Path("id") id: Int): List<PictureDto>

    @GET("api/v1/venues/{id}/")
    suspend fun getVenueDetails(@Path("id") id: Int): VenueDetailsDto
}
