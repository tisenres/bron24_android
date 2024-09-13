package com.bron24.bron24_android.data.network.apiservices

import com.bron24.bron24_android.data.network.dto.booking.BookingDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface BookingApiService {
    @POST("bookings")
    suspend fun createBooking(@Body bookingDto: BookingDto): BookingDto

    @GET("bookings/{bookingId}")
    suspend fun getBookingById(@Path("bookingId") bookingId: String): BookingDto

    @POST("bookings/{bookingId}/cancel")
    suspend fun cancelBooking(@Path("bookingId") bookingId: String)
}
