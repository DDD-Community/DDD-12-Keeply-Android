package com.keeply.presentation.ui.folder

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.keeply.presentation.core.components.KeeplyText
import com.keeply.presentation.core.theme.KeeplyTheme

@Composable
fun FolderRoute() {
    FolderScreen()
}

@Composable
fun FolderScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        KeeplyText(
            text = "FolderText",
            style = KeeplyTheme.typography.header01
        )
    }
}