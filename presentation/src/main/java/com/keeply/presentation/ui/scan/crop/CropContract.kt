package com.keeply.presentation.ui.scan.crop

import androidx.compose.runtime.Immutable
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size

@Immutable
data class CropState(
    val title: String = "크롭",
    val uri: String = "",
    val cropRect: Rect = Rect.Zero,
    val imageSize: Size = Size.Zero
)

sealed interface CropSideEffect {
    data class CropCompleted(val croppedImageUri: String) : CropSideEffect
}