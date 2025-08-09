package com.keeply.presentation.ui.folder.detail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.keeply.presentation.core.components.KeeplyButton
import com.keeply.presentation.core.components.KeeplyButtonStyle
import com.keeply.presentation.core.components.KeeplyTextField
import com.keeply.presentation.core.components.colorBar.ColorBar
import com.keeply.presentation.core.components.colorBar.FolderColor
import com.keeply.presentation.core.theme.KeeplyTheme

@Composable
fun FolderModifyBottomSheetContent(
    initialFolderName: String = "",
    initialSelectedColor: FolderColor,
    onDeleteClick: () -> Unit = {},
    onSaveClick: (String, FolderColor) -> Unit = { _, _ -> }
) {
    var folderName by remember { mutableStateOf(initialFolderName) }
    var selectedColor by remember { mutableStateOf(initialSelectedColor) }
    val checkFolderRegex = folderName.length in 1..20 && folderName.isNotBlank()
    
    Column(
        modifier = Modifier
            .padding(
                top = 24.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 20.dp
            )
    ) {
        KeeplyTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = folderName,
            onValueChange = { folderName = it },
            helpIcon = if (checkFolderRegex) null else KeeplyTheme.icons.error,
            helpText = if (checkFolderRegex) "" else "최대 20자까지 입력 가능합니다.",
        )

        ColorBar(
            modifier = Modifier
                .padding(top = 20.dp),
            selectedColor = selectedColor,
            onColorSelected = { selectedColor = it }
        )

        Row(
            modifier = Modifier
                .padding(top = 32.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            KeeplyButton(
                modifier = Modifier
                    .weight(1f),
                text = "폴더 삭제",
                onClick = onDeleteClick,
                buttonStyle = KeeplyButtonStyle.SECONDARY
            )

            KeeplyButton(
                modifier = Modifier
                    .weight(1f),
                text = "저장",
                onClick = { onSaveClick(folderName, selectedColor) },
                enabled = checkFolderRegex
            )
        }
    }
}

@Preview
@Composable
private fun FolderModifyBottomSheetContentPreview() {
    KeeplyTheme {
        FolderModifyBottomSheetContent(
            initialFolderName = "나의 폴더",
            initialSelectedColor = FolderColor.YELLOW
        )
    }
}