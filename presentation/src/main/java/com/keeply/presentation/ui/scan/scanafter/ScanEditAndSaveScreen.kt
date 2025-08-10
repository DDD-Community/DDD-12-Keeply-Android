package com.keeply.presentation.ui.scan.scanafter

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.keeply.domain.folder.model.Folder
import com.keeply.presentation.R
import com.keeply.presentation.core.components.FolderList
import com.keeply.presentation.core.components.ImageFrame
import com.keeply.presentation.core.components.InsightTextField
import com.keeply.presentation.core.components.KeeplyButton
import com.keeply.presentation.core.components.KeeplyButtonSize
import com.keeply.presentation.core.components.KeeplyButtonStyle
import com.keeply.presentation.core.components.KeeplyIconButton
import com.keeply.presentation.core.components.KeeplyText
import com.keeply.presentation.core.theme.KeeplyTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.net.URLDecoder

@Composable
fun ScanEditAndSaveScreen(
    uri: Uri? = null,
    textField: String,
    textFieldLength: Int,
    textFieldMaxLength: Int,
    folderList: ImmutableList<Folder>,
    onValueChange: (String) -> Unit,
    onBackClick: () -> Unit,
    onSaveClick: (String, Long) -> Unit
) {
    // TODO: 수정 필요
    val isSavable by remember { mutableStateOf(textFieldLength <= textFieldMaxLength) }

    Column {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            // TODO: image사이즈에 맞춰지도록 수정 필요
            item {
                val painter = if (LocalInspectionMode.current) {
                    painterResource(id = R.drawable.img_onboarding_02)
                } else {
                    rememberAsyncImagePainter(URLDecoder.decode(uri.toString(), "UTF-8"))
                }

                ImageFrame(
                    painter = painter,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 19.dp, bottom = 40.dp),
                )
            }

            item {
                KeeplyText(
                    text = stringResource(R.string.scan_insight_title),
                    style = KeeplyTheme.typography.header04,
                )

                InsightTextField(
                    value = textField,
                    onValueChange = onValueChange,
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 40.dp),
                    placeholder = stringResource(R.string.scan_insight_text_field),
                    maxLength = textFieldMaxLength
                )
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    KeeplyText(
                        modifier = Modifier
                            .weight(1f),
                        text = stringResource(R.string.scan_folder_title),
                        style = KeeplyTheme.typography.header04,
                    )

                    KeeplyIconButton(
                        painter = KeeplyTheme.icons.add,
                        onClick = { /* TODO : 폴더 추가 로직 */ }
                    )
                }
            }

            itemsIndexed(folderList) { index, folder ->
                FolderList(
                    modifier = Modifier
                        .padding(top = if (index == 0) 16.dp else 6.dp, bottom = 6.dp),
                    folder = folder
                )
            }

            item {
                Spacer(modifier = Modifier.height(9.dp))
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, start = 16.dp, end = 16.dp, bottom = 32.dp)
        ) {
            KeeplyButton(
                onClick = onBackClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                text = stringResource(R.string.scan_back),
                buttonStyle = KeeplyButtonStyle.SECONDARY,
                buttonSize = KeeplyButtonSize.MEDIUM
            )

            KeeplyButton(
                onClick = {
                    // TODO: 폴더 일단 1로 고정함 -> 폴더 선택 처리, 선택시 UI 적용 필요
                    onSaveClick(textField, 1)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                enabled = isSavable,
                text = stringResource(R.string.scan_save),
                buttonSize = KeeplyButtonSize.MEDIUM
            )
        }
    }
}


@Composable
fun FolderTextList(
    modifier: Modifier = Modifier,
    text: String,
    onClick: (Boolean) -> Unit
) {
    var isSelected by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .background(if (isSelected) KeeplyTheme.colors.neutral1000 else KeeplyTheme.colors.neutral100)
            .fillMaxWidth()
            .clickable {
                isSelected = !isSelected
                onClick(isSelected)
            }
            .padding(all = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        KeeplyText(
            modifier = Modifier
                .padding(start = 4.dp)
                .weight(1f),
            text = text,
            style = KeeplyTheme.typography.button01Suit,
            color = if (isSelected) KeeplyTheme.colors.neutralWhite else KeeplyTheme.colors.neutralBlack,
        )

        Icon(
            modifier = Modifier
                .padding(start = 20.dp)
                .size(16.dp),
            painter = KeeplyTheme.icons.checkmark,
            contentDescription = "",
            tint = if (isSelected) KeeplyTheme.colors.neutralWhite else KeeplyTheme.colors.neutral300,
        )
    }
}

@Preview(showBackground = true, heightDp = 1000)
@Composable
private fun ScanScreenPreview() {
    KeeplyTheme {
        ScanEditAndSaveScreen(
            textField = "",
            textFieldLength = 0,
            textFieldMaxLength = 300,
            onValueChange = { },
            onBackClick = { },
            onSaveClick = { _, _ -> },
            folderList = persistentListOf(
                Folder(
                    folderId = 1,
                    folderName = "독서",
                    color = "",
                    imageCount = 3,
                    updatedAt = "0000"
                ),
                Folder(
                    folderId = 1,
                    folderName = "독서",
                    color = "",
                    imageCount = 3,
                    updatedAt = "0000"
                )
            )
        )
    }
}