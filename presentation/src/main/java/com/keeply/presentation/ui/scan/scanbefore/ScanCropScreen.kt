package com.keeply.presentation.ui.scan.scanbefore

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun ScanCropRoute(
    onBack: () -> Unit,
    onCropped: (Uri) -> Unit,
    viewModel: ScanBeforeViewModel = hiltViewModel()
) {
    val uiState by viewModel.collectAsState()

    ScanCropScreen(
        uri = uiState.uri.toUri(),
        onBack = onBack,
        onCropped = { }
    )
}

@Composable
fun ScanCropScreen(
    uri: Uri? = null,
    onBack: () -> Unit,
    onCropped: (Uri) -> Unit
) {
    // TODO: 크롭기능 구현

}