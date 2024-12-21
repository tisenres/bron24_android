package com.bron24.bron24_android.components.toast

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

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