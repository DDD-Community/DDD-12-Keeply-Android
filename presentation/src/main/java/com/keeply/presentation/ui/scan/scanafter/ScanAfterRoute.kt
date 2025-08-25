package com.keeply.presentation.ui.scan.scanafter

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import com.keeply.presentation.ui.folder.add.AddFolderSideEffect
import com.keeply.presentation.ui.folder.add.AddFolderViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun ScanAfterRoute(
    onBack: () -> Unit,
    onNavigateToHome: () -> Unit,
    viewModel: ScanAfterViewModel = hiltViewModel(),
    addFolderViewModel: AddFolderViewModel = hiltViewModel()
) {
    val uiState by viewModel.collectAsState()
    val addFolderState by addFolderViewModel.collectAsState()

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
        selectedFolderId = uiState.selectedFolderId,
        onAddFolderClick = { viewModel.onAddFolderModal(true) },
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

    if (uiState.showAddFolderModal) {
        addFolderViewModel.collectSideEffect { sideEffect ->
            when (sideEffect) {
                is AddFolderSideEffect.ShowCreateSuccess -> {
                    viewModel.onAddFolderModal(false)
                    viewModel.loadFolders()
                    if (sideEffect.isDuplicate && sideEffect.duplicatedMessage.isNullOrBlank().not()) {
                        Toast.makeText(context, sideEffect.duplicatedMessage, Toast.LENGTH_SHORT).show()
                    }
                }

                is AddFolderSideEffect.ShowError -> {
                    Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        AddFolderModal(
            state = addFolderState,
            onFolderNameChange = addFolderViewModel::updateFolderName,
            onColorSelect = addFolderViewModel::selectColor,
            onCreateFolder = addFolderViewModel::createFolder,
            onNavigateBack = { viewModel.onAddFolderModal(false) },
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