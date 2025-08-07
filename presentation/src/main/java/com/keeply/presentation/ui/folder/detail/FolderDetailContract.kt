package com.keeply.presentation.ui.folder.detail

import androidx.compose.runtime.Immutable
import com.keeply.domain.folder.model.FolderImage
import com.keeply.presentation.core.components.colorBar.FolderColor
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class FolderDetailState(
    val folderId: Long = 0L,
    val folderName: String = "",
    val images: ImmutableList<FolderImage> = persistentListOf(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isShowBottomSheet: Boolean = false,
    val editingFolderName: String = "",
    val selectedColor: FolderColor = FolderColor.YELLOW,
    val hasUpdated: Boolean = false,
    val isShowDeleteDialog: Boolean = false
)

sealed interface FolderDetailSideEffect {
    data class ShowError(val message: String) : FolderDetailSideEffect
    data object NavigateBackWithRefresh : FolderDetailSideEffect
}

