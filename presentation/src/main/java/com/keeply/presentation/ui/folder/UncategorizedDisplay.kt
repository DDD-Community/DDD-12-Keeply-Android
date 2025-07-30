package com.keeply.presentation.ui.folder

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.keeply.presentation.core.components.KeeplyText
import com.keeply.presentation.core.theme.KeeplyTheme

@Composable
fun UncategorizedDisplay() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        KeeplyText(
            "미분류 스크린샷",
            style = KeeplyTheme.typography.header01
        )
    }
}