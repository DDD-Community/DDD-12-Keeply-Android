package com.keeply.presentation.ui.scan

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.keeply.presentation.core.components.KeeplyText
import com.keeply.presentation.core.theme.KeeplyTheme

@Composable
fun ScanRoute() {
    ScanScreen()
}

@Composable
fun ScanScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        KeeplyText(
            text = "ScanText",
            style = KeeplyTheme.typography.header01
        )
    }
}