package com.bron24.bron24_android.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

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
