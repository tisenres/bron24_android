package com.bron24.bron24_android.data.network.apiservices

import com.bron24.bron24_android.data.network.dto.booking.AvailableTimesRequestDto
import com.bron24.bron24_android.data.network.dto.booking.AvailableTimesResponseDto
import com.bron24.bron24_android.data.network.dto.booking.RequestBookingDto
import com.bron24.bron24_android.data.network.dto.booking.ResponseBookingDto
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface BookingApiService {
    @POST("api/v1/booking/available/")
    suspend fun getAvailableTimes(@Body request: AvailableTimesRequestDto): AvailableTimesResponseDto

    @POST("api/v1/booking/book/")
    suspend fun startBooking(
        @Body request: RequestBookingDto,
        @Query("book") book: Boolean = false
    ): ResponseBookingDto

    @POST("api/v1/booking/book/")
    suspend fun finishBooking(
        @Body request: RequestBookingDto,
        @Query("book") book: Boolean = true
    ): ResponseBookingDto
}
