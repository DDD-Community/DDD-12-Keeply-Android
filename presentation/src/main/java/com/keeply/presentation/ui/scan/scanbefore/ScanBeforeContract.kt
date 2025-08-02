package com.keeply.presentation.ui.scan.scanbefore

import androidx.compose.runtime.Immutable
import com.keeply.domain.model.ScanAnalyze

@Immutable
data class ScanBeforeState(
    val uri: String,
    val textField: String = "",
    val textFieldMaxLength: Int = 300,
    val isScanCompleted: Boolean = false,
    val ocrResult: ScanAnalyze? = null
)

sealed interface ScanBeforeSideEffect