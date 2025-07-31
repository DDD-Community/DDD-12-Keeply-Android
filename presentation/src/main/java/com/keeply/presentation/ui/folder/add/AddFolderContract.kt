package com.keeply.presentation.ui.folder.add

import androidx.compose.runtime.Immutable
import com.keeply.presentation.core.components.colorBar.FolderColor

@Immutable
data class AddFolderState(
    val folderName: String = "",
    val folderColor: FolderColor = FolderColor.ORANGE
)

sealed interface AddFolderSideEffect {
    data object NavigateBack : AddFolderSideEffect
    data object ShowCreateSuccess : AddFolderSideEffect
}