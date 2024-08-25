package com.bron24.bron24_android.helper.util.presentation.components

import kotlinx.coroutines.flow.asSharedFlow
import androidx.compose.runtime.*
import kotlinx.coroutines.flow.MutableSharedFlow

object NetworkErrorToast {
    private val _showToast = MutableSharedFlow<Unit>()
    val showToast = _showToast.asSharedFlow()

    suspend fun show() {
        _showToast.emit(Unit)
    }
}

@Composable
fun NetworkErrorToastHandler() {
    var showToast by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        NetworkErrorToast.showToast.collect {
            showToast = true
        }
    }

    if (showToast) {
        CustomToast(
            message = "Network error",
            type = ToastType.ERROR,
            onDismiss = { showToast = false }
        )
    }
}