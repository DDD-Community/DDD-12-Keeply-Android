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

// 각 색상에 대한 16진수 값 정의 (AARRGGBB 형식)
fun FolderColor.toHexString(): String {
    return when (this) {
        FolderColor.ORANGE -> "FFFF4400"  // orange400
        FolderColor.YELLOW -> "FFFFC453"  // folderYellow
        FolderColor.GRAY -> "FFBDBDBF"    // neutral400
        FolderColor.BLUE -> "FF7AB9F2"    // folderBlue
        FolderColor.PURPLE -> "FFBBA6F4"  // folderPurple
        FolderColor.BEIGE -> "FFD4BCA1"   // folderBeige
        FolderColor.BLACK -> "FF000000"   // neutralBlack
    }
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