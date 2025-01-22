package com.bron24.bron24_android.di

import com.bron24.bron24_android.data.network.apiservices.AuthApi
import com.bron24.bron24_android.data.network.apiservices.BookingApiService
import com.bron24.bron24_android.data.network.apiservices.OrdersApi
import com.bron24.bron24_android.data.network.apiservices.ProfileApi
import com.bron24.bron24_android.data.network.apiservices.VenueApiService
import com.bron24.bron24_android.helper.util.Public
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    @Provides
    @Singleton
    fun provideVenueApiService(@Public retrofit: Retrofit): VenueApiService =
        retrofit.create(VenueApiService::class.java)

    @Provides
    @Singleton
    fun provideAuthApiService(retrofit: Retrofit): AuthApi =
        retrofit.create(AuthApi::class.java)

    @Provides
    @Singleton
    fun provideBookingApiService(@Public retrofit: Retrofit): BookingApiService =
        retrofit.create(BookingApiService::class.java)


    @Provides
    @Singleton
    fun provideOrdersApi(@Public retrofit: Retrofit): OrdersApi =
        retrofit.create(OrdersApi::class.java)

    @Provides
    @Singleton
    fun provideProfileApi(@Public retrofit: Retrofit): ProfileApi =
        retrofit.create(ProfileApi::class.java)
}