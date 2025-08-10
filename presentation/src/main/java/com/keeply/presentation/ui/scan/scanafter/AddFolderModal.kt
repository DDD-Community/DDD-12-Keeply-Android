package com.keeply.presentation.ui.scan.scanafter

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.keeply.presentation.core.components.BaseAlertModal
import com.keeply.presentation.core.components.KeeplyButton
import com.keeply.presentation.core.components.KeeplyButtonSize
import com.keeply.presentation.core.components.KeeplyButtonStyle
import com.keeply.presentation.core.components.KeeplyText
import com.keeply.presentation.core.components.KeeplyTextField
import com.keeply.presentation.core.components.colorBar.ColorBar
import com.keeply.presentation.core.components.colorBar.FolderColor
import com.keeply.presentation.core.theme.KeeplyTheme
import com.keeply.presentation.core.theme.neutral200
import com.keeply.presentation.core.theme.neutral600
import com.keeply.presentation.ui.folder.add.AddFolderState

@Composable
fun AddFolderModal(
    state: AddFolderState = AddFolderState(),
    onFolderNameChange: (String) -> Unit = {},
    onColorSelect: (FolderColor) -> Unit = {},
    onCreateFolder: () -> Unit = {},
    onNavigateBack: () -> Unit = {},
) {
    BaseAlertModal {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 7.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween

            ) {
                KeeplyText(
                    text = "새 폴더",
                    KeeplyTheme.typography.subtitle01
                )

                Icon(
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { onNavigateBack() },
                    painter = KeeplyTheme.icons.close,
                    contentDescription = "닫기",
                    tint = KeeplyTheme.colors.neutral400,
                )
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(neutral200)
            )

            KeeplyTextField(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .background(KeeplyTheme.colors.neutral100)
                    .fillMaxWidth(),
                value = state.folderName,
                onValueChange = onFolderNameChange,
                helpIcon = if (state.checkFolderRegex()) null else KeeplyTheme.icons.error,
                helpText = if (state.checkFolderRegex()) "" else "최대 20자까지 입력 가능합니다.",
                placeholder = "폴더명"
            )

            KeeplyText(
                text = "컬러",
                style = KeeplyTheme.typography.body,
                modifier = Modifier.padding(top = 16.dp),
                color = neutral600
            )

            ColorBar(
                modifier = Modifier
                    .padding(top = 8.dp),
                selectedColor = state.folderColor,
                onColorSelected = onColorSelect
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                horizontalArrangement = Arrangement.End,
            ) {
                KeeplyButton(
                    modifier = Modifier,
                    text = "취소",
                    textStyle = KeeplyTheme.typography.button01Suit,
                    buttonStyle = KeeplyButtonStyle.SECONDARY,
                    buttonSize = KeeplyButtonSize.XSMALL,
                    onClick = { onNavigateBack() }
                )

                Spacer(modifier = Modifier.width(12.dp))

                KeeplyButton(
                    modifier = Modifier,
                    text = "추가하기",
                    textStyle = KeeplyTheme.typography.button01Suit,
                    buttonSize = KeeplyButtonSize.XSMALL,
                    onClick = { onCreateFolder() },
                    enabled = true
                )
            }
        }
    }
}

@Preview
@Composable
fun AddFolderModalPreview() {
    KeeplyTheme {
        AddFolderModal()
    }
}