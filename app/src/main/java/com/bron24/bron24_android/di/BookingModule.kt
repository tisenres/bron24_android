package com.bron24.bron24_android.di

import com.bron24.bron24_android.data.network.apiservices.BookingApiService
import com.bron24.bron24_android.data.repository.BookingRepositoryImpl
import com.bron24.bron24_android.domain.repository.BookingRepository
import com.bron24.bron24_android.domain.usecases.booking.CreateBookingUseCase
import com.bron24.bron24_android.domain.usecases.booking.GetBookingDetailsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BookingModule {

//    @Provides
//    @Singleton
//    fun provideBookingApiService(retrofit: Retrofit): BookingApiService =
//        retrofit.create(BookingApiService::class.java)
//
//    @Provides
//    @Singleton
//    fun provideBookingRepository(
//        bookingApiService: BookingApiService,
//    ): BookingRepository =
//        BookingRepositoryImpl(bookingApiService)
//
//    @Provides
//    fun provideCreateBookingUseCase(repository: BookingRepository): CreateBookingUseCase =
//        CreateBookingUseCase(repository)
//
//    @Provides
//    fun provideGetBookingDetailsUseCase(repository: BookingRepository): GetBookingDetailsUseCase =
//        GetBookingDetailsUseCase(repository)

}
