package com.keeply.presentation.ui.scan.scanafter

import androidx.compose.runtime.Immutable

@Immutable
data class ScanAfterState(
    val uri: String,
    val cachedImageId: String? = null,
    val detectedText: String? = null,
    val recommendedTags: List<String>? = null,
    val textField: String = "",
    val textFieldMaxLength: Int = 300
)

sealed interface ScanAfterSideEffect