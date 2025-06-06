//package com.bron24.bron24_android.data.network.interceptors
//
//import android.util.Log
//import com.bron24.bron24_android.components.toast.ToastManager
//import com.bron24.bron24_android.components.toast.ToastType
//import kotlinx.coroutines.MainScope
//import kotlinx.coroutines.flow.MutableSharedFlow
//import kotlinx.coroutines.launch
//import java.io.IOException
//import javax.inject.Singleton
//
//sealed class AppError {
//    data object NetworkError : AppError()
//    data class HttpError(val code: Int, val message: String) : AppError()
//    data class UnknownError(val message: String) : AppError()
//    data object ServerUnavailableError : AppError()
//    data object TimeoutError : AppError()
//    data class ParseError(val message: String) : AppError()
//    data object UnresolvedHostError : AppError()
//    data object NoConnectivity : AppError()
//}
//
//class NoConnectivityException : IOException("No network available, please check your WiFi or Data connection")
//
//@Singleton
//class ErrorHandler {
//    private val _errorFlow = MutableSharedFlow<AppError>()
//
//    fun handleError(error: AppError) {
//        MainScope().launch {
//            _errorFlow.emit(error)
//            showErrorToast(error)
//            logError(error)
//        }
//    }
//
//    private fun showErrorToast(error: AppError) {
//        val message = when (error) {
//            is AppError.NetworkError -> "No internet connection. Please check your connection and try again."
//            is AppError.NoConnectivity -> "No network available. Please check your WiFi or Data connection."
//            is AppError.HttpError -> when (error.code) {
//                401 -> "Unauthorized. Please log in again."
//                403 -> "Access forbidden. You don't have permission to access this resource."
//                404 -> "Resource not found. The requested data is unavailable."
//                429 -> "Too many requests. Please try again later."
//                500 -> "Server error. We're working on fixing this issue."
//                503 -> "Service unavailable. Please try again later."
//                else -> "An error occurred: ${error.code} - ${error.message}"
//            }
//            is AppError.UnknownError -> "An unexpected error occurred: ${error.message}"
//            is AppError.ServerUnavailableError -> "The server is currently unavailable. Please try again later."
//            is AppError.TimeoutError -> "The request timed out. Please check your connection and try again."
//            is AppError.ParseError -> "An error occurred while processing the data: ${error.message}"
//            is AppError.UnresolvedHostError -> "Unable to resolve host. Please check your connection and try again."
//        }
//        Log.e("Error_handled", message)
//        ToastManager.showToast(message, ToastType.ERROR)
//    }
//
//    private fun logError(error: AppError) {
//        val logMessage = when (error) {
//            is AppError.NetworkError -> "Network Error"
//            is AppError.HttpError -> "HTTP Error: ${error.code} - ${error.message}"
//            is AppError.UnknownError -> "Unknown Error: ${error.message}"
//            is AppError.ServerUnavailableError -> "Server Unavailable Error"
//            is AppError.TimeoutError -> "Timeout Error"
//            is AppError.NoConnectivity -> "No Internet Connection"
//            is AppError.ParseError -> "Parse Error: ${error.message}"
//            is AppError.UnresolvedHostError -> "Unresolved Host Error"
//        }
//        Log.e("AppError", logMessage)
//    }
//}