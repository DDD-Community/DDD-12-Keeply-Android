package com.keeply.presentation.ui.permission

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.keeply.presentation.core.components.KeeplyButton
import com.keeply.presentation.core.components.KeeplyButtonStyle
import com.keeply.presentation.core.components.KeeplyText
import com.keeply.presentation.core.theme.KeeplyTheme

@Composable
fun PermissionUpgradeDialog(
    onDismiss: () -> Unit = {},
    onConfirm: () -> Unit = {}
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = KeeplyTheme.colors.neutralWhite
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                KeeplyText(
                    text = "갤러리에 대한 권한 사용을 거부했습니다. 주요 기능 사용을 위해 사진 접근 권한을 허용해 주세요.",
                    style = KeeplyTheme.typography.body,
                    color = KeeplyTheme.colors.neutralBlack,
                    textAlign = TextAlign.Center
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 28.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    KeeplyButton(
                        modifier = Modifier
                            .weight(1f),
                        text = "닫기",
                        buttonStyle = KeeplyButtonStyle.SECONDARY,
                        onClick = {
                            onDismiss()
                        }
                    )

                    KeeplyButton(
                        modifier = Modifier
                            .weight(1f),
                        text = "권한 설정",
                        onClick = {
                            onConfirm()
                        }
                    )
                }
            }
        }
    }
}