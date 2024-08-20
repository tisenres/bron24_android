package com.bron24.bron24_android.data.network.interceptors

import android.util.Log
import com.bron24.bron24_android.domain.repository.AuthRepository
import com.bron24.bron24_android.domain.repository.TokenRepository
import dagger.Lazy
import kotlinx.coroutines.runBlocking
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ErrorHandlingCallAdapterFactory(
    private val authRepository: Lazy<AuthRepository>,
    private val tokenRepository: TokenRepository
) : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != Call::class.java) return null
        val responseType = getParameterUpperBound(0, returnType as ParameterizedType)
        return ErrorHandlingCallAdapter<Any>(responseType, authRepository, tokenRepository)
    }

    private class ErrorHandlingCallAdapter<R>(
        private val responseType: Type,
        private val authRepository: Lazy<AuthRepository>,
        private val tokenRepository: TokenRepository
    ) : CallAdapter<R, Call<R>> {

        override fun responseType() = responseType

        override fun adapt(call: Call<R>): Call<R> {
            return ErrorHandlingCall(call, authRepository, tokenRepository)
        }
    }

    private class ErrorHandlingCall<R>(
        private val delegate: Call<R>,
        private val authRepository: Lazy<AuthRepository>,
        private val tokenRepository: TokenRepository
    ) : Call<R> {

        override fun enqueue(callback: Callback<R>) {
            delegate.enqueue(object : Callback<R> {
                override fun onResponse(call: Call<R>, response: Response<R>) {
                    when (response.code()) {
                        401 -> handleUnauthorized(call, callback)
                        else -> callback.onResponse(call, response)
                    }
                }

                override fun onFailure(call: Call<R>, t: Throwable) {
                    callback.onFailure(call, t)
                }
            })
        }

        override fun execute(): Response<R> {
            val response = delegate.execute()
            return when (response.code()) {
                401 -> handleUnauthorizedSync() ?: response
                else -> response
            }
        }

        private fun handleUnauthorized(call: Call<R>, callback: Callback<R>) {
            val refreshToken = tokenRepository.getRefreshToken()
            if (refreshToken != null) {
                val refreshed = runBlocking {
                    try {
                        authRepository.get().refreshAndSaveTokens(refreshToken)
                    } catch (e: Exception) {
                        Log.e("ErrorHandlingCall", "Token refresh failed", e)
                        false
                    }
                }
                if (refreshed) {
                    val newCall = clone()
                    newCall.enqueue(callback)
                } else {
                    authRepository.get().handleRefreshFailure()
                    // Instead of propagating the error, handle it silently or with an appropriate response.
                    callback.onResponse(
                        call,
                        Response.error(200, okhttp3.ResponseBody.create(null, ""))
                    )
                }
            } else {
                authRepository.get().handleRefreshFailure()
                callback.onResponse(
                    call,
                    Response.error(200, okhttp3.ResponseBody.create(null, ""))
                )
            }
        }

        private fun handleUnauthorizedSync(): Response<R>? {
            val refreshToken = tokenRepository.getRefreshToken()
            return if (refreshToken != null) {
                val refreshed = runBlocking {
                    try {
                        authRepository.get().refreshAndSaveTokens(refreshToken)
                    } catch (e: Exception) {
                        Log.e("ErrorHandlingCall", "Token refresh failed", e)
                        false
                    }
                }
                if (refreshed) {
                    clone().execute()
                } else {
                    authRepository.get().handleRefreshFailure()
                    // Handle the error silently or with an appropriate response.
                    Response.success(null)
                }
            } else {
                authRepository.get().handleRefreshFailure()
                Response.success(null)
            }
        }

        override fun clone() = ErrorHandlingCall(delegate.clone(), authRepository, tokenRepository)
        override fun isExecuted() = delegate.isExecuted
        override fun cancel() = delegate.cancel()
        override fun isCanceled() = delegate.isCanceled
        override fun request(): Request = delegate.request()
        override fun timeout(): Timeout = delegate.timeout()
    }
}