package com.keeply.presentation.ui.alarm

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.keeply.presentation.core.components.KeeplyText
import com.keeply.presentation.core.theme.KeeplyTheme

@Composable
fun AlarmRoute() {
    AlarmScreen()
}

@Composable
fun AlarmScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        KeeplyText(
            text = "AlarmText",
            style = KeeplyTheme.typography.header01
        )
    }
}