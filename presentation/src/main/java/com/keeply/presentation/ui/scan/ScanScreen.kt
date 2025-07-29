package com.keeply.presentation.ui.scan

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ScanRoute(
    onBack: () -> Unit
) {
    ScanScreen(onBack = onBack)
}

@Composable
fun ScanScreen(
    viewModel: LocalScreenshotViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    ScreenshotScreen(viewModel, onBack)
}