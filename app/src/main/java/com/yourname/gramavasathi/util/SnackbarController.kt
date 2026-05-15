package com.yourname.gramavasathi.util

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

data class SnackbarEvent(
    val message: String,
    val actionLabel: String? = null
)

object SnackbarController {
    private val _events = MutableSharedFlow<SnackbarEvent>()
    val events = _events.asSharedFlow()

    suspend fun sendEvent(event: SnackbarEvent) {
        _events.emit(event)
    }
}