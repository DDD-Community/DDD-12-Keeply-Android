package com.keeply.presentation.ui.my.setting.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.keeply.presentation.core.components.KeeplyText
import com.keeply.presentation.core.components.KeeplyToggle
import com.keeply.presentation.core.theme.KeeplyTheme

@Composable
fun SettingToggleItem(
    modifier: Modifier = Modifier,
    text: String,
    isChecked: Boolean = false,
    onToggleValueChange: (Boolean) -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(46.dp),
        verticalAlignment = Alignment.CenterVertically,
    ){
        KeeplyText(
            modifier = Modifier
                .weight(1f),
            text = text,
            style = KeeplyTheme.typography.caption01,
        )

        KeeplyToggle(
            checked = isChecked,
            onClick = onToggleValueChange
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingToggleItemPreview() {
    KeeplyTheme {
        SettingToggleItem(
            text = "알림 설정",
            isChecked = true,
        )
    }
}