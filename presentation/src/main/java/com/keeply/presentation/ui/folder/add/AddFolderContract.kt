package com.keeply.presentation.ui.folder.add

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.keeply.presentation.core.theme.KeeplyTheme

@Immutable
data class AddFolderState(
    val folderName: String = "",
)

sealed interface AddFolderSideEffect