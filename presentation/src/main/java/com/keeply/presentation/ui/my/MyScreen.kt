package com.keeply.presentation.ui.my

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.keeply.presentation.core.components.KeeplyText
import com.keeply.presentation.core.theme.KeeplyTheme

@Composable
fun MyRoute() {
    MyScreen()
}

@Composable
fun MyScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        KeeplyText(
            text = "MyText",
            style = KeeplyTheme.typography.header01
        )
    }
}