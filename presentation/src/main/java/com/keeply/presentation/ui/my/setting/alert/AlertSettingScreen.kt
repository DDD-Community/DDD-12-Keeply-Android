package com.keeply.presentation.ui.my.setting.alert

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.keeply.presentation.core.components.KeeplyAppBar
import com.keeply.presentation.core.theme.KeeplyTheme

@Composable
fun SettingAlertRoute(
    viewModel: SettingAlertViewModel = hiltViewModel()
) {
    SettingAlertScreen()
}

@Composable
fun SettingAlertScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(KeeplyTheme.colors.neutral100)
    ) {
        KeeplyAppBar(
            title = "알림 설정",
            leadingIcon = {
                Icon(
                    painter = KeeplyTheme.icons.chevronLeft,
                    contentDescription = "Back",
                    tint = KeeplyTheme.colors.neutralBlack
                )
            },
            onClickLeading = {  },
        )
    }
}

@Preview
@Composable
fun SettingAlertScreenPreview() {
    KeeplyTheme {
        SettingAlertScreen()
    }
}