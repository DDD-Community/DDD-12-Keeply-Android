package com.keeply.presentation.core.components.colorBar

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.keeply.presentation.core.theme.KeeplyTheme

enum class FolderColor {
    ORANGE,
    YELLOW,
    GRAY,
    BLUE,
    PURPLE,
    BEIGE,
    BLACK
}

@Composable
fun FolderColor.toComposeColor(): Color {
    return when (this) {
        FolderColor.ORANGE -> KeeplyTheme.colors.orange400
        FolderColor.YELLOW -> KeeplyTheme.colors.folderYellow
        FolderColor.BEIGE -> KeeplyTheme.colors.folderBeige
        FolderColor.BLUE -> KeeplyTheme.colors.folderBlue
        FolderColor.PURPLE -> KeeplyTheme.colors.folderPurple
        FolderColor.GRAY -> KeeplyTheme.colors.neutral400
        FolderColor.BLACK -> KeeplyTheme.colors.neutralBlack
    }
}