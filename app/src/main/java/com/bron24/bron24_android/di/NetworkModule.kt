package com.bron24.bron24_android.di

import android.content.Context
import android.util.Log
import com.bron24.bron24_android.BuildConfig
import com.bron24.bron24_android.data.network.interceptors.HttpInterceptor
import com.bron24.bron24_android.domain.repository.TokenRepository
import com.bron24.bron24_android.domain.usecases.language.GetSelectedLanguageUseCase
import com.bron24.bron24_android.helper.util.AuthAuthenticator
import com.bron24.bron24_android.helper.util.Public
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @[Provides Singleton]
    fun providesChuck(@ApplicationContext context: Context): ChuckerInterceptor =
        ChuckerInterceptor.Builder(context).build()

    @Provides
    @Singleton
    fun providesOkHttpClient(
        chuckerInterceptor: ChuckerInterceptor,
        httpInterceptor: HttpInterceptor,
    ): OkHttpClient {

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addInterceptor(chuckerInterceptor)
            .addInterceptor(httpInterceptor)
            .apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(logging)
                }
            }
            .build()
    }

    @Provides
    @Singleton
    @Public
    fun provideOkHttpClient(
        tokenRepository: TokenRepository,
        chuckerInterceptor: ChuckerInterceptor,
        httpInterceptor: HttpInterceptor,
        authAuthenticator: AuthAuthenticator,
        getSelectedLanguageUseCase: GetSelectedLanguageUseCase
    ): OkHttpClient {

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addInterceptor(chuckerInterceptor)
            .addInterceptor(httpInterceptor)
            .authenticator(authAuthenticator)
            .addNetworkInterceptor { chain ->
                Log.d("AAA", "provideOkHttpClient: ${getSelectedLanguageUseCase.invoke().languageCode}")
                val token = tokenRepository.getAccessToken() ?: ""
                val lanCode = getSelectedLanguageUseCase.invoke().languageCode ?: ""
                val newRequest = chain.request().newBuilder()
                newRequest.header("Authorization", "Bearer $token")
                newRequest.header("Accept-Language", lanCode)
                chain.proceed(newRequest.build())
            }.apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(logging)
                }
            }
            .build()
    }

    @[Provides Singleton]
    fun provideBaseRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("http://13.60.36.176:8000/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @[Provides Singleton Public]
    fun providePublicRetrofit(@Public okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("http://13.60.36.176:8000/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}