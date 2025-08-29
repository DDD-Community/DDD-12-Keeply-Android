package com.keeply.presentation.ui.scan.screenshot

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import com.keeply.domain.model.Screenshot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Immutable
data class ScreenshotState(
    val screenshotsFlow: Flow<PagingData<Screenshot>> = flowOf(PagingData.empty()),
    val isRestricted: Boolean = false,
    val showPermissionDeniedDialog: Boolean = false
)

sealed interface ScreenshotSideEffect {
    data object RequestPermission : ScreenshotSideEffect
}