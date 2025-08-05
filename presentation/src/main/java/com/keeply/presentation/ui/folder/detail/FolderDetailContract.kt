package com.keeply.presentation.ui.folder.detail

import androidx.compose.runtime.Immutable
import com.keeply.domain.folder.model.FolderImage
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class FolderDetailState(
    val folderId: Long = 0L,
    val folderName: String = "",
    val images: ImmutableList<FolderImage> = persistentListOf(),
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed interface FolderDetailSideEffect {
    data class ShowError(val message: String) : FolderDetailSideEffect
}

