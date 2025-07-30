package com.keeply.presentation.ui.scan.scanbefore

import androidx.compose.runtime.Immutable

@Immutable
data class ScanBeforeState(
    val uri: String
)

sealed interface ScanBeforeSideEffect