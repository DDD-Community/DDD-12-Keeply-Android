package com.keeply.presentation.ui.scan.crop

import androidx.compose.runtime.Immutable

@Immutable
data class CropState(
    val title: String = "크롭",
    val uri: String = ""
)

sealed interface CropSideEffect