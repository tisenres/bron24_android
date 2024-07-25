package com.bron24.bron24_android.features.venuelisting.data.remote

import com.bron24.bron24_android.features.venuelisting.data.dto.VenueDto
import retrofit2.http.GET

interface VenueApiService {
    @GET("api/v1/venues/")
    suspend fun getVenues(): List<VenueDto>
}
