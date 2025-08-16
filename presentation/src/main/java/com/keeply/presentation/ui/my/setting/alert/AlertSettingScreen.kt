package com.keeply.presentation.ui.my.setting.alert

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.keeply.presentation.core.components.KeeplyAppBar
import com.keeply.presentation.core.components.KeeplyToggle
import com.keeply.presentation.core.theme.KeeplyTheme
import com.keeply.presentation.ui.my.setting.component.SettingToggleItem

@Composable
fun SettingAlertRoute(
    viewModel: SettingAlertViewModel = hiltViewModel(),
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
            onClickLeading = { },
        )

        Column(
            modifier = Modifier
                .padding(
                    top = 11.dp,
                    start = 16.dp,
                    end = 16.dp
                )
        ) {
            SettingToggleItem(
                text = "저장 용량 알림",
                isChecked = true,
                onToggleValueChange = {}
            )

            SettingToggleItem(
                text = "마케팅 알림",
                isChecked = false,
                onToggleValueChange = {}
            )

            SettingToggleItem(
                text = "알림 권한",
                isChecked = false,
                onToggleValueChange = {}
            )
        }
    }
}

@Preview
@Composable
fun SettingAlertScreenPreview() {
    KeeplyTheme {
        SettingAlertScreen()
    }
}