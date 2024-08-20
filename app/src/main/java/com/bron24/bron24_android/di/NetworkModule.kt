package com.bron24.bron24_android.di

import com.bron24.bron24_android.data.network.apiservices.AuthApiService
import com.bron24.bron24_android.data.network.apiservices.VenueApiService
import com.bron24.bron24_android.data.network.interceptors.AuthInterceptor
import com.bron24.bron24_android.data.network.interceptors.ErrorHandlingCallAdapterFactory
import com.bron24.bron24_android.domain.repository.AuthRepository
import com.bron24.bron24_android.domain.repository.TokenRepository
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    @Named("BaseRetrofit")
    fun provideBaseRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://cuddly-becoming-loon.ngrok-free.app/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @Named("ErrorHandlingRetrofit")
    fun provideErrorHandlingRetrofit(
        okHttpClient: OkHttpClient,
        authRepository: Lazy<AuthRepository>,
        tokenRepository: TokenRepository
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://cuddly-becoming-loon.ngrok-free.app/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(ErrorHandlingCallAdapterFactory(authRepository, tokenRepository))
            .build()
    }

    @Provides
    @Singleton
    fun provideVenueApiService(@Named("ErrorHandlingRetrofit") retrofit: Retrofit): VenueApiService {
        return retrofit.create(VenueApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthApiService(@Named("BaseRetrofit") retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }
}