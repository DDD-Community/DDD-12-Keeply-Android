package com.keeply.presentation.ui.scan.scanafter

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.keeply.presentation.core.theme.neutralWhite
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun ScanAfterRoute(
    onBack: () -> Unit,
    onSave: () -> Unit,
    viewModel: ScanAfterViewModel = hiltViewModel()
) {
    val uiState by viewModel.collectAsState()

    ScanAfterScreen(
        uri = uiState.uri.toUri(),
        textField = uiState.textField,
        textFieldMaxLength = uiState.textFieldMaxLength,
        onBack = onBack,
        onSave = onSave,
        onValueChange = viewModel::onValueChange,
        recommendedTags = uiState.recommendedTags
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanAfterScreen(
    uri: Uri? = null,

    textField: String,
    textFieldMaxLength: Int,
    onValueChange: (String) -> Unit,
    recommendedTags: List<String>? = null,

    onBack: () -> Unit,
    onSave: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    var showSelectBottomSheet by remember { mutableStateOf(true) }


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(neutral100) // #F4F4F4 해야되는데 없어서 임시
    ) {
        if (showSelectBottomSheet) {
            SelectTextBottomSheet(
                modifier = Modifier,
                sheetState = sheetState,
                recommendedTags = recommendedTags

            )
        }

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectTextBottomSheet(
    modifier: Modifier,
    sheetState: SheetState,
    recommendedTags: List<String>?
) {
    Log.d("TAG", "SelectTextBottomSheet: $recommendedTags")
    ModalBottomSheet(
        onDismissRequest = {
            // 아무것도 선택 안 하고 다음
//                showSelectBottomSheet = false
        },
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        containerColor = neutralWhite,
        modifier = modifier,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(437.dp)
                .padding(top = 24.dp, start = 16.dp, end = 16.dp, bottom = 32.dp)
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.TopStart)

            ) {
                KeeplyText(
                    text = "Text",
                    style = KeeplyTheme.typography.header04,
                )

                KeeplyText(
                    modifier = Modifier
                        .padding(top = 6.dp),
                    text = "기록에 활용할 문구를 선택해주세요.",
                    style = KeeplyTheme.typography.body,
                )
            }

            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(top = 28.dp)
            ) {
                // TODO: 리스트 표시
                recommendedTags?.let {
                    repeat(it.size) {
                        val recommendedTag = recommendedTags[it]
                        FolderList(
                            modifier = Modifier
                                .padding(vertical = 6.dp),
                            tag = recommendedTag
                        )
                    }
                }

            }

            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(top = 32.dp)
                    .align(Alignment.BottomEnd),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                KeeplyButton(
                    onClick = { },
                    modifier = Modifier
                        .width(75.dp),
                    text = "취소",
                    buttonStyle = KeeplyButtonStyle.SECONDARY,
                    buttonSize = KeeplyButtonSize.SMALL
                )
                KeeplyButton(
                    onClick = { },
                    modifier = Modifier
                        .width(75.dp),
                    enabled = false,
                    text = "이동",
                    buttonSize = KeeplyButtonSize.SMALL
                )
            }
        }
    }
}

@Composable
fun FolderList(
    modifier: Modifier = Modifier,
    tag: String
) {
    Row(
        modifier = modifier
            .background(neutralWhite)
            .fillMaxWidth()
            .padding(all = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .width(1.dp)
                .height(10.dp)
                .background(KeeplyTheme.colors.neutral200)
        )
        KeeplyText(
            modifier = Modifier
                .padding(start = 4.dp),
            text = tag,
            style = KeeplyTheme.typography.body,
            color = KeeplyTheme.colors.neutral500
        )
    }
}


//@OptIn(ExperimentalMaterial3Api::class)
//@Preview
//@Composable
//private fun SelectTextBottomSheetPreview() {
//    KeeplyTheme {
//        SelectTextBottomSheet(
//            sheetState = rememberModalBottomSheetState(),
//            modifier = Modifier
//        )
//    }
//}


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