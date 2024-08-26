package com.bron24.bron24_android.helper.util.presentation.components.toast

import androidx.compose.runtime.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

//@Composable
//fun ToastManager(content: @Composable (showToast: (String, ToastType) -> Unit) -> Unit) {
//    var toastMessage by remember { mutableStateOf<String?>(null) }
//    var toastType by remember { mutableStateOf<ToastType?>(null) }
//
//    content { message, type ->
//        toastMessage = message
//        toastType = type
//    }
//
//    toastMessage?.let { message ->
//        toastType?.let { type ->
//            CustomToast(
//                message = message,
//                type = type,
//                onDismiss = {
//                    toastMessage = null
//                    toastType = null
//                }
//            )
//        }
//    }
//}

object ToastManager {
    private val _toastData = MutableStateFlow<ToastData?>(null)
    val toastData: StateFlow<ToastData?> = _toastData

    fun showToast(message: String, type: ToastType, onDismiss: (() -> Unit)? = null) {
        _toastData.value = ToastData(message, type, onDismiss)
    }

    fun clearToast() {
        _toastData.value?.onDismiss?.invoke()
        _toastData.value = null
    }
}

data class ToastData(
    val message: String,
    val type: ToastType,
    val onDismiss: (() -> Unit)?
)