package com.keeply.presentation.ui.scan.scanbefore

import androidx.compose.runtime.Immutable

@Immutable
data class ScanBeforeState(
    val uri: String,
    val textField: String = "",
    val textFieldMaxLength: Int = 300
)

sealed interface ScanBeforeSideEffect