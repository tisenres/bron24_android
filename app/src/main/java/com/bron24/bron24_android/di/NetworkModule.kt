package com.bron24.bron24_android.di

import com.bron24.bron24_android.data.network.apiservices.AuthApiService
import com.bron24.bron24_android.data.network.apiservices.VenueApiService
import com.bron24.bron24_android.data.network.interceptors.ErrorHandler
import com.bron24.bron24_android.data.network.interceptors.HttpInterceptor
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
    fun provideErrorHandler(): ErrorHandler {
        return ErrorHandler()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: HttpInterceptor): OkHttpClient {
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
//            .baseUrl("http://109.123.241.109:46343/") // Real OTP
            .baseUrl("http://91.211.249.185:8000/") // Test OTP via webhook
//            .baseUrl("https://ebd8-82-215-105-180.ngrok-free.app/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideErrorHandlingCallAdapterFactory(
        authRepository: Lazy<AuthRepository>,
        tokenRepository: TokenRepository,
        errorHandler: ErrorHandler
    ): ErrorHandlingCallAdapterFactory {
        return ErrorHandlingCallAdapterFactory(authRepository, tokenRepository, errorHandler)
    }

    @Provides
    @Singleton
    @Named("ErrorHandlingRetrofit")
    fun provideErrorHandlingRetrofit(
        okHttpClient: OkHttpClient,
        errorHandlingCallAdapterFactory: ErrorHandlingCallAdapterFactory
    ): Retrofit {
        return Retrofit.Builder()
//            .baseUrl("http://109.123.241.109:46343/") // Real OTP
            .baseUrl("http://91.211.249.185:8000/") // Test OTP via webhook
//            .baseUrl("https://ebd8-82-215-105-180.ngrok-free.app/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(errorHandlingCallAdapterFactory)
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