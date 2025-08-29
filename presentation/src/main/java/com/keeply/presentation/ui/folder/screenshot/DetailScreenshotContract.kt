package com.keeply.presentation.ui.folder.screenshot

import androidx.compose.runtime.Immutable

@Immutable
data class DetailScreenshotState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val imageId: Long = 0,
    val folderName: String,
    val folderColor: String,
    val presignedUrl: String = "",
    val tag: String = "",
    val textField: String = "",
    val textFieldLength: Int = 0,
    val textFieldMaxLength: Int = 300,
)

sealed interface DetailScreenshotSideEffect {

}