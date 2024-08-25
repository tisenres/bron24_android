package com.bron24.bron24_android.helper.util.presentation.components

import androidx.compose.runtime.*

@Composable
fun ToastManager(content: @Composable (showToast: (String, ToastType) -> Unit) -> Unit) {
    var toastMessage by remember { mutableStateOf<String?>(null) }
    var toastType by remember { mutableStateOf<ToastType?>(null) }

    content { message, type ->
        toastMessage = message
        toastType = type
    }

    toastMessage?.let { message ->
        toastType?.let { type ->
            CustomToast(
                message = message,
                type = type,
                onDismiss = {
                    toastMessage = null
                    toastType = null
                }
            )
        }
    }
}