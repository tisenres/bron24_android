//package com.bron24.bron24_android.data.network.interceptors
//
//import android.util.Log
//import com.bron24.bron24_android.domain.repository.AuthRepository
//import com.bron24.bron24_android.domain.repository.TokenRepository
//import dagger.Lazy
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.runBlocking
//import okhttp3.Request
//import okhttp3.ResponseBody.Companion.toResponseBody
//import okio.Timeout
//import retrofit2.Call
//import retrofit2.CallAdapter
//import retrofit2.Callback
//import retrofit2.HttpException
//import retrofit2.Response
//import retrofit2.Retrofit
//import java.io.IOException
//import java.lang.reflect.ParameterizedType
//import java.lang.reflect.Type
//import java.net.SocketTimeoutException
//import java.net.UnknownHostException
//
//class ErrorHandlingCallAdapterFactory(
//    private val authRepository: Lazy<AuthRepository>,
//    private val tokenRepository: TokenRepository,
//    private val errorHandler: ErrorHandler
//) : CallAdapter.Factory() {
//
//    override fun get(
//        returnType: Type,
//        annotations: Array<out Annotation>,
//        retrofit: Retrofit
//    ): CallAdapter<*, *>? {
//        if (getRawType(returnType) != Call::class.java) return null
//        val responseType = getParameterUpperBound(0, returnType as ParameterizedType)
//        return ErrorHandlingCallAdapter<Any>(
//            responseType,
//            authRepository,
//            tokenRepository,
//            errorHandler
//        )
//    }
//
//    private class ErrorHandlingCallAdapter<R>(
//        private val responseType: Type,
//        private val authRepository: Lazy<AuthRepository>,
//        private val tokenRepository: TokenRepository,
//        private val errorHandler: ErrorHandler
//    ) : CallAdapter<R, Call<R>> {
//
//        override fun responseType() = responseType
//
//        override fun adapt(call: Call<R>): Call<R> {
//            return ErrorHandlingCall(call, authRepository, tokenRepository, errorHandler)
//        }
//    }
//
//    private class ErrorHandlingCall<R>(
//        private val delegate: Call<R>,
//        private val authRepository: Lazy<AuthRepository>,
//        private val tokenRepository: TokenRepository,
//        private val errorHandler: ErrorHandler
//    ) : Call<R> {
//
//        override fun enqueue(callback: Callback<R>) {
//            delegate.enqueue(object : Callback<R> {
//                override fun onResponse(call: Call<R>, response: Response<R>) {
//                    when (response.code()) {
//                        in 200..299 -> callback.onResponse(call, response)
//                        401 -> CoroutineScope(Dispatchers.IO).launch {
//                           // handleUnauthorized(call, callback)
//                        }
//
//                        in 400..499 -> handleClientError(call, response, callback)
//                        in 500..599 -> handleServerError(call, response, callback)
//                        else -> handleUnknownError(call, response, callback)
//                    }
//                }
//
//                override fun onFailure(call: Call<R>, t: Throwable) {
//                    handleFailure(call, t, callback)
//                }
//            })
//        }
//
////        private fun handleFailure(call: Call<R>, t: Throwable, callback: Callback<R>) {
////            CoroutineScope(Dispatchers.Main).launch {
////                when (t) {
////                    is UnknownHostException -> {
////                        errorHandler.handleError(AppError.UnresolvedHostError)
////                        callback.onResponse(call, Response.success(null))
////                    }
////                    is SocketTimeoutException -> {
////                        errorHandler.handleError(AppError.TimeoutError)
////                        callback.onResponse(call, Response.success(null))
////                    }
////                    is IOException -> {
////                        errorHandler.handleError(AppError.NetworkError)
////                        callback.onResponse(call, Response.success(null))
////                    }
////                    is HttpException -> {
////                        errorHandler.handleError(AppError.HttpError(t.code(), t.message()))
////                        callback.onResponse(call, Response.success(null))
////                    }
////                    else -> {
////                        errorHandler.handleError(AppError.UnknownError(t.message ?: "Unknown error occurred"))
////                        callback.onResponse(call, Response.success(null))
////                    }
////                }
////            }
////        }
//
//        private fun handleFailure(call: Call<R>, t: Throwable, callback: Callback<R>) {
//            CoroutineScope(Dispatchers.Main).launch {
//                val error = when (t) {
//                    is NoConnectivityException -> AppError.NoConnectivity
//                    is UnknownHostException -> AppError.UnresolvedHostError
//                    is SocketTimeoutException -> AppError.TimeoutError
//                    is IOException -> AppError.NetworkError
//                    is HttpException -> AppError.HttpError(t.code(), t.message())
//                    else -> AppError.UnknownError(t.message ?: "Unknown error occurred")
//                }
//                errorHandler.handleError(error)
//                callback.onResponse(call, Response.success(null))
//            }
//        }
//
//        override fun execute(): Response<R> {
//            return try {
//                val response = delegate.execute()
//                when (response.code()) {
//                    in 200..299 -> response
//                    //401 -> handleUnauthorizedSync() ?: response
//                    in 400..499 -> handleClientErrorSync(response)
//                    in 500..599 -> handleServerErrorSync(response)
//                    else -> handleUnknownErrorSync(response)
//                }
//            } catch (e: IOException) {
//                handleNetworkErrorSync(e)
//            } catch (e: Exception) {
//                handleUnknownErrorSync(
//                    Response.error(
//                        500,
//                        "Unknown error occurred".toResponseBody(null)
//                    )
//                )
//            }
//        }
//
////        private suspend fun handleUnauthorized(call: Call<R>, callback: Callback<R>) {
////            val refreshToken = tokenRepository.getRefreshToken()
////            if (refreshToken != null) {
////                val refreshed = runBlocking {
////                    try {
////                        authRepository.get.(refreshToken)
////                    } catch (e: Exception) {
////                        errorHandler.handleError(
////                            AppError.UnknownError(
////                                e.message ?: "Token refresh failed"
////                            )
////                        )
////                        false
////                    }
////                }
////                if (refreshed) {
////                    val newCall = clone()
////                    newCall.enqueue(callback)
////                } else {
////                    authRepository.get().handleRefreshFailure()
////                    errorHandler.handleError(AppError.HttpError(401, "Unauthorized"))
////                    callback.onResponse(
////                        call,
////                        Response.error(401, okhttp3.ResponseBody.create(null, "Unauthorized"))
////                    )
////                }
////            } else {
////                authRepository.get().handleRefreshFailure()
////                errorHandler.handleError(AppError.HttpError(401, "Unauthorized"))
////                callback.onResponse(
////                    call,
////                    Response.error(401, okhttp3.ResponseBody.create(null, "Unauthorized"))
////                )
////            }
////        }
//
//        private fun handleClientError(call: Call<R>, response: Response<R>, callback: Callback<R>) {
//            CoroutineScope(Dispatchers.IO).launch {
//                try {
//                    errorHandler.handleError(
//                        AppError.HttpError(
//                            response.code(),
//                            response.message()
//                        )
//                    )
//                    callback.onResponse(call, response)
//                } catch (e: Exception) {
//                    // Log the exception to understand what's going wrong
//                    Log.e("ErrorHandling", "Exception during error handling: ${e.message}")
//                    callback.onFailure(call, e)
//                }
//            }
//        }
//
//        private fun handleServerError(call: Call<R>, response: Response<R>, callback: Callback<R>) {
//            CoroutineScope(Dispatchers.Main).launch {
//                try {
//                    Log.e(
//                        "ErrorHandling",
//                        "Handling server error: ${response.code()} - ${response.message()}"
//                    )
//                    errorHandler.handleError(
//                        AppError.HttpError(
//                            response.code(),
//                            response.message()
//                        )
//                    )
//                    callback.onResponse(call, response)
//                } catch (e: Exception) {
//                    Log.e("ErrorHandling", "Exception during error handling: ${e.message}")
//                    callback.onFailure(call, e)
//                }
//            }
//        }
//
//        private fun handleUnknownError(
//            call: Call<R>,
//            response: Response<R>,
//            callback: Callback<R>
//        ) {
//            runBlocking {
//                errorHandler.handleError(AppError.UnknownError(response.message()))
//            }
//            callback.onResponse(call, response)
//        }
//
////        private fun handleFailure(call: Call<R>, t: Throwable, callback: Callback<R>) {
////            runBlocking {
////                when (t) {
////                    is IOException -> errorHandler.handleError(AppError.NetworkError)
////                    is HttpException -> errorHandler.handleError(AppError.HttpError(t.code(), t.message()))
////                    else -> errorHandler.handleError(AppError.UnknownError(t.message ?: "Unknown error occurred"))
////                }
////            }
////            callback.onFailure(call, t)
////        }
//
////        private fun handleUnauthorizedSync(): Response<R>? {
////            val refreshToken = tokenRepository.getRefreshToken()
////            return if (refreshToken != null) {
////                val refreshed = runBlocking {
////                    try {
////                        //authRepository.get().refreshAndSaveTokens(refreshToken)
////                    } catch (e: Exception) {
////                        errorHandler.handleError(
////                            AppError.UnknownError(
////                                e.message ?: "Token refresh failed"
////                            )
////                        )
////                        false
////                    }
////                }
////                if (refreshed) {
////                    clone().execute()
////                } else {
////                    authRepository.get().handleRefreshFailure()
////                    runBlocking {
////                        errorHandler.handleError(
////                            AppError.HttpError(
////                                401,
////                                "Unauthorized"
////                            )
////                        )
////                    }
////                    Response.error(401, okhttp3.ResponseBody.create(null, "Unauthorized"))
////                }
////            } else {
////                authRepository.get().handleRefreshFailure()
////                runBlocking { errorHandler.handleError(AppError.HttpError(401, "Unauthorized")) }
////                Response.error(401, okhttp3.ResponseBody.create(null, "Unauthorized"))
////            }
////        }
//
//        private fun handleClientErrorSync(response: Response<R>): Response<R> {
//            runBlocking {
//                errorHandler.handleError(AppError.HttpError(response.code(), response.message()))
//            }
//            return response
//        }
//
//        private fun handleServerErrorSync(response: Response<R>): Response<R> {
//            runBlocking {
//                errorHandler.handleError(AppError.HttpError(response.code(), response.message()))
//            }
//            return response
//        }
//
//        private fun handleUnknownErrorSync(response: Response<R>): Response<R> {
//            runBlocking {
//                errorHandler.handleError(AppError.UnknownError(response.message()))
//            }
//            return response
//        }
//
//        private fun handleNetworkErrorSync(e: IOException): Response<R> {
//            runBlocking {
//                errorHandler.handleError(AppError.NetworkError)
//            }
//            return Response.error(500, okhttp3.ResponseBody.create(null, "Network error occurred"))
//        }
//
//        override fun clone() =
//            ErrorHandlingCall(delegate.clone(), authRepository, tokenRepository, errorHandler)
//
//        override fun isExecuted() = delegate.isExecuted
//        override fun cancel() = delegate.cancel()
//        override fun isCanceled() = delegate.isCanceled
//        override fun request(): Request = delegate.request()
//        override fun timeout(): Timeout = delegate.timeout()
//    }
//}