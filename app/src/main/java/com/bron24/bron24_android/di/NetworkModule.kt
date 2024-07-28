package com.bron24.bron24_android.di

import com.bron24.bron24_android.data.network.VenueApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
//            .baseUrl("http://127.0.0.1:8000/") // This maps to the host machine's localhost
            .baseUrl("http://10.0.2.2:8000/") // This maps to the host machine's localhost
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideVenueApiService(retrofit: Retrofit): VenueApiService {
        return retrofit.create(VenueApiService::class.java)
    }
}