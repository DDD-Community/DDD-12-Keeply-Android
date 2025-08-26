package com.keeply.presentation.ui.folder.add

import androidx.compose.runtime.Immutable
import com.keeply.presentation.core.components.colorBar.FolderColor

@Immutable
data class AddFolderState(
    val folderName: String = "",
    val folderColor: FolderColor = FolderColor.ORANGE
) {
    fun checkFolderRegex() = folderName.length in 1..20 && folderName.isNotBlank()
}

sealed interface AddFolderSideEffect {
    data class ShowCreateSuccess(val isDuplicate: Boolean = false, val duplicatedMessage: String?) : AddFolderSideEffect
    data class ShowError(val message: String) : AddFolderSideEffect
}