package com.keeply.presentation.ui.scan.scanafter

import androidx.compose.runtime.Immutable
import com.keeply.domain.folder.model.Folder
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class ScanAfterState(
    val uri: String,
    val cachedImageId: String = "",
    val detectedText: String? = null,
    val recommendedTags: ImmutableList<String>? = null,
    val detectedTextList: ImmutableList<String> = persistentListOf(),
    val selectedIndices: ImmutableList<Int> = persistentListOf(),
    val textField: String = "",
    val textFieldLength: Int = 0,
    val textFieldMaxLength: Int = 300,
    val isLoading: Boolean = false,
    val folders: ImmutableList<Folder> = persistentListOf(),
    val selectedFolderId: Long = -1L,
    val error: String? = null,
    val showSelectTextBottomSheet: Boolean = true, // 초기값 설정
    val showAddFolderModal: Boolean = false,
    val showSuccessModal: Boolean = false
)

sealed interface ScanAfterSideEffect {
    data class ShowError(val message: String) : ScanAfterSideEffect
    data class ShowSuccessModal(val imageId: Long) : ScanAfterSideEffect
}