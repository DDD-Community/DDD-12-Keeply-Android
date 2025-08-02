package com.keeply.presentation.ui.scan.scanafter

import androidx.compose.runtime.Immutable

@Immutable
data class ScanAfterState(
    val uri: String,
    val cachedImageId: String = "",
    val detectedText: String? = null,
    val recommendedTags: List<String>? = null,
    val textField: String = "",
    val textFieldMaxLength: Int = 300,
    val isLoading: Boolean = false
)

sealed interface ScanAfterSideEffect {
    data class ShowError(val message: String) : ScanAfterSideEffect
    data class NavigateToSuccess(val imageId: Long) : ScanAfterSideEffect
}