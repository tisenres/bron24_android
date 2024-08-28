package com.bron24.bron24_android.data.network.interceptors

import android.util.Log
import com.bron24.bron24_android.helper.util.presentation.components.toast.ToastManager
import com.bron24.bron24_android.helper.util.presentation.components.toast.ToastType
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Singleton

sealed class AppError {
    data object NetworkError : AppError()
    data class HttpError(val code: Int, val message: String) : AppError()
    data class UnknownError(val message: String) : AppError()
}

@Singleton
class ErrorHandler {
    private val _errorFlow = MutableSharedFlow<AppError>()

    suspend fun handleError(error: AppError) {
        _errorFlow.emit(error)
        showErrorToast(error)
    }

    private fun showErrorToast(error: AppError) {
        val message = when (error) {
            is AppError.NetworkError -> "No internet connection"
            is AppError.HttpError -> when (error.code) {
                401 -> "Unauthorized. Please log in again."
                404 -> "Resource not found"
                500 -> "Server error. Please try again later."
                else -> "HTTP Error: ${error.code} - ${error.message}"
            }
            is AppError.UnknownError -> error.message
        }
        Log.e("Error_handled", message)
        ToastManager.showToast(
            message,
            ToastType.ERROR
        )
    }
}