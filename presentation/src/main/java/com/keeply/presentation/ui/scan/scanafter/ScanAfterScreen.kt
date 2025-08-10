package com.keeply.presentation.ui.scan.scanafter

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import com.keeply.domain.folder.model.Folder
import com.keeply.presentation.core.theme.neutral100
import kotlinx.collections.immutable.ImmutableList
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

    var showSuccessModal by remember { mutableStateOf(false) }

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is ScanAfterSideEffect.ShowError -> {
                Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
            }

            is ScanAfterSideEffect.ShowSuccessModal -> {
                showSuccessModal = true
            }
        }
    }

    BackHandler { onBack.invoke() }

    ScanAfterScreen(
        uri = uiState.uri.toUri(),
        textField = uiState.textField,
        textFieldLength = uiState.textFieldLength,
        textFieldMaxLength = uiState.textFieldMaxLength,
        detectedTextList = uiState.detectedTextList,
        folderList = uiState.folders,
        onValueChange = viewModel::onValueChange,
        onNextClick = { selectedIndices ->
            viewModel.updateSelectedIndices(selectedIndices)
        },
        onBackClick = onBack,
        onSaveClick = { selectedText, folderId ->
            viewModel.onSaveClick(selectedText, folderId)
        },
        isLoading = uiState.isLoading
    )

    if (showSuccessModal) {
        ImageSaveCompleteModal(
            onNavigateToFolder = {
                showSuccessModal = false
                onNavigateToHome()
            }
        )
    }
}

@Composable
fun ScanAfterScreen(
    uri: Uri? = null,
    detectedTextList: List<String>,
    textField: String,
    textFieldLength: Int,
    textFieldMaxLength: Int,
    folderList: ImmutableList<Folder>,
    onValueChange: (String) -> Unit,
    onNextClick: (List<Int>) -> Unit,
    onBackClick: () -> Unit,
    onSaveClick: (String, Long) -> Unit,
    isLoading: Boolean
) {
    var isSheetVisible by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(neutral100)
    ) {
        if (isSheetVisible) {
            SelectTextBottomSheet(
                textList = detectedTextList,
                onBack = {
                    isSheetVisible = false
                },
                onNextClick = { selectedIndices ->
                    onNextClick(selectedIndices)
                    isSheetVisible = false
                },
                isLoading = isLoading
            )
        }

        ScanEditAndSaveScreen(
            uri = uri,
            textField = textField,
            textFieldLength = textFieldLength,
            textFieldMaxLength = textFieldMaxLength,
            folderList = folderList,
            onValueChange = onValueChange,
            onBackClick = onBackClick,
            onSaveClick = onSaveClick
        )
    }
}