package com.keeply.presentation.ui.onboarding.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.keeply.presentation.R
import com.keeply.presentation.core.components.FolderIcon
import com.keeply.presentation.core.components.KeeplyButton
import com.keeply.presentation.core.components.KeeplyText
import com.keeply.presentation.core.theme.KeeplyTheme

@Composable
fun PermissionRequestDialog(
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
                FolderIcon(
                    tint = KeeplyTheme.colors.orange400
                )

                Spacer(modifier = Modifier.height(20.dp))
                
                KeeplyText(
                    text = "사진 접근 권한이 필요해요",
                    style = KeeplyTheme.typography.subtitle01,
                    color = KeeplyTheme.colors.neutralBlack
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                KeeplyText(
                    text = "Keeply가 갤러리의 스크린샷을\n불러오기 위해 권한이 필요합니다",
                    style = KeeplyTheme.typography.body,
                    color = KeeplyTheme.colors.neutral600,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                KeeplyButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "권한 허용하기",
                    onClick = onConfirm
                )
            }
        }
    }
}