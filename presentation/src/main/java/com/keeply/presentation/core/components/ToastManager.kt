package com.keeply.presentation.core.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object ToastManager {
    private var toastJob: Job? = null
    private val scope = CoroutineScope(Dispatchers.Main)
    
    var toastState by mutableStateOf<ToastData?>(null)
        private set
    
    fun show(
        title: String,
        content: String,
        contentAlignment: Alignment = Alignment.BottomCenter,
        duration: Long = 3000L
    ) {
        toastJob?.cancel()
        toastState = ToastData(
            title = title,
            content = content,
            contentAlignment = contentAlignment
        )
        
        toastJob = scope.launch {
            delay(duration)
            hide()
        }
    }
    
    fun hide() {
        toastJob?.cancel()
        toastState = null
    }
    
    data class ToastData(
        val title: String,
        val content: String,
        val contentAlignment: Alignment
    )
}