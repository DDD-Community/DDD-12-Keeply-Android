package com.keeply.presentation.ui.scan.scanafter

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun ScanAfterRoute(
    onBack: () -> Unit,
    onNavigateToHome: () -> Unit,
    viewModel: ScanAfterViewModel = hiltViewModel()
) {
    val uiState by viewModel.collectAsState()
    val context = LocalContext.current

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is ScanAfterSideEffect.ShowError -> {
                Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
            }

            is ScanAfterSideEffect.ShowSuccessModal -> {

            }
        }
    }

    BackHandler { onBack.invoke() }

    ScanEditAndSaveScreen(
        uri = uiState.uri.toUri(),
        textField = uiState.textField,
        textFieldLength = uiState.textFieldLength,
        textFieldMaxLength = uiState.textFieldMaxLength,
        folderList = uiState.folders,
        onTextChange = viewModel::onTextChange,
        onSelectFolder = viewModel::onSelectFolder,
        onBackClick = onBack,
        onSaveClick = viewModel::onSaveClick
    )

    if (uiState.showSelectTextBottomSheet) {
        SelectTextBottomSheet(
            textList = uiState.detectedTextList,
            onBack = {
                viewModel.dismissSuccessModal()
            },
            onNextClick = { selectedIndices ->
                viewModel.updateSelectedIndices(selectedIndices)
                viewModel.onFinishedTextSelection()
            },
            isLoading = uiState.isLoading
        )
    }

    if (uiState.showSuccessModal) {
        ImageSaveCompleteModal(
            onNavigateToFolder = {
                viewModel.dismissSuccessModal()
                onNavigateToHome()
            }
        )
    }
}