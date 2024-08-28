package com.bron24.bron24_android.helper.util.presentation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.runBlocking

object GlobalAuthEventBus {
    private val _authEvents = MutableSharedFlow<AuthEvent>(replay = 0, extraBufferCapacity = 1)
    val authEvents: SharedFlow<AuthEvent> = _authEvents.asSharedFlow()

    fun postEventBlocking(event: AuthEvent) {
        runBlocking { _authEvents.emit(event) }
    }
}

sealed class AuthEvent {
    object TokenRefreshFailed : AuthEvent()
}
