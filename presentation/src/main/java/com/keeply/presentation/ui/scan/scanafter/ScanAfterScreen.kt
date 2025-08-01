package com.keeply.presentation.ui.scan.scanafter

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.keeply.presentation.core.components.FolderList
import com.keeply.presentation.core.components.ImageFrame
import com.keeply.presentation.core.components.InsightTextField
import com.keeply.presentation.core.components.KeeplyButton
import com.keeply.presentation.core.components.KeeplyButtonSize
import com.keeply.presentation.core.components.KeeplyButtonStyle
import com.keeply.presentation.core.components.KeeplyIconButton
import com.keeply.presentation.core.components.KeeplyText
import com.keeply.presentation.core.theme.KeeplyTheme
import com.keeply.presentation.core.theme.neutral100
import com.keeply.presentation.ui.scan.scanbefore.ScanBeforeViewModel
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun ScanAfterRoute(
    onBack: () -> Unit,
    onSave: () -> Unit,
    onValueChange: (String) -> Unit,
    viewModel: ScanBeforeViewModel = hiltViewModel()
) {
    val uiState by viewModel.collectAsState()

    ScanAfterScreen(
        uri = uiState.uri.toUri(),
        textField = uiState.textField,
        textFieldMaxLength = uiState.textFieldMaxLength,
        onBack = onBack,
        onSave = onSave,
        onValueChange = onValueChange
    )
}

@Composable
fun ScanAfterScreen(
    uri: Uri? = null,

    textField: String,
    textFieldMaxLength: Int,
    onValueChange: (String) -> Unit,

    onBack: () -> Unit,
    onSave: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(neutral100) // #F4F4F4 해야되는데 없어서 임시
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .align(Alignment.TopCenter)
        ) {
            ImageFrame(
                painter = rememberAsyncImagePainter(uri),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 25.dp, start = 22.dp, end = 22.dp)
            )

            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            ) {
                KeeplyText(
                    modifier = Modifier
                        .padding(top = 40.dp),
                    text = "Text",
                    style = KeeplyTheme.typography.header04,
                )

                InsightTextField(
                    value = textField,
                    onValueChange = onValueChange,
                    modifier = Modifier
                        .padding(top = 16.dp),
                    placeholder = "인사이트를 적어주세요.",
                    maxLength = textFieldMaxLength
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    KeeplyText(
                        modifier = Modifier
                            .weight(1f),
                        text = "Folder",
                        style = KeeplyTheme.typography.header04,
                    )

                    KeeplyIconButton(
                        painter = KeeplyTheme.icons.add,
                    )
                }

                Column(
                    modifier = Modifier
                        .padding(top = 16.dp)
                ) {
                    repeat(4) {
                        FolderList(
                            modifier = Modifier
                                .padding(vertical = 6.dp)
                        )
                    }
                }
            }

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(neutral100) // #F4F4F4 해야되는데 없어서 임시
                .padding(top = 12.dp, start = 16.dp, end = 16.dp, bottom = 32.dp)
                .align(Alignment.BottomCenter),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            KeeplyButton(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                text = "취소",
                buttonStyle = KeeplyButtonStyle.SECONDARY,
                buttonSize = KeeplyButtonSize.MEDIUM
            )
            KeeplyButton(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                enabled = false,
                text = "이동",
                buttonSize = KeeplyButtonSize.MEDIUM
            )
        }
    }
}

@Preview(showBackground = true, heightDp = 750)
@Composable
private fun ScanScreenPreview() {
    KeeplyTheme {
        ScanAfterScreen(
            textField = "",
            textFieldMaxLength = 300,
            onBack = { },
            onSave = { },
            onValueChange = { }
        )
    }
}