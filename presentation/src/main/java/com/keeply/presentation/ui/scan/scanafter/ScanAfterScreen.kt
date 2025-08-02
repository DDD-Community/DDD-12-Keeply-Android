package com.keeply.presentation.ui.scan.scanafter

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.platform.LocalContext
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
import java.net.URLDecoder

@Composable
fun ScanAfterRoute(
    onBack: () -> Unit,
    onSave: () -> Unit,
    viewModel: ScanAfterViewModel = hiltViewModel()
) {
    val uiState by viewModel.collectAsState()
    val context = LocalContext.current

    ScanAfterScreen(
        uri = uiState.uri.toUri(),
        context = context,
        textField = uiState.textField,
        textFieldMaxLength = uiState.textFieldMaxLength,
        onBack = onBack,
        onSave = onSave,
        onValueChange = viewModel::onValueChange,
        cachedImageId = uiState.cachedImageId,
        recommendedTags = uiState.recommendedTags,
        detectedText = uiState.detectedText
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanAfterScreen(
    uri: Uri? = null,

    context: Context,
    textField: String,
    textFieldMaxLength: Int,
    onValueChange: (String) -> Unit,
    cachedImageId: String?,
    recommendedTags: List<String>? = null,
    detectedText: String?,

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
        if (showSelectBottomSheet ) {
            SelectTextBottomSheet(
                modifier = Modifier,
                context = context,
                sheetState = sheetState,
                cachedImageId = cachedImageId,
                recommendedTags = recommendedTags,
                detectedText = detectedText ?: "추출된 텍스트가 없습니다."

            )
        }

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .align(Alignment.TopCenter)
        ) {
            Log.d("TAG", "ScanAfterScreen: $uri")
            ImageFrame(
                painter = rememberAsyncImagePainter(URLDecoder.decode(uri.toString(), "UTF-8")),
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
    context: Context,
    sheetState: SheetState,
    cachedImageId: String?,
    recommendedTags: List<String>?,
    detectedText: String
) {
    if (cachedImageId.isNullOrBlank()) return // 추후수정

    var resultString by remember { mutableStateOf("") }

    Log.d("TAG", "SelectTextBottomSheet: $recommendedTags")
    ModalBottomSheet(
        onDismissRequest = {
            // 아무것도 선택 안 하고 다음
//                showSelectBottomSheet = false
            Toast.makeText(
                context,
                "이제 앱 종료 후 안내에 따라 진행해 주세요.",
                Toast.LENGTH_SHORT
            ).show()
        },
        dragHandle = null,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        containerColor = neutralWhite,
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(437.dp)
                .padding(top = 24.dp, start = 16.dp, end = 16.dp, bottom = 32.dp)
        ) {
            Column(
                modifier = Modifier
                    .background(neutralWhite)

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
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(top = 28.dp)
            ) {
                val lineList: List<String> = detectedText.split("\n")

                repeat(lineList.size) {
                    val keyword = lineList[it]
                    FolderTextList(
                        modifier = Modifier
                            .padding(vertical = 6.dp),
                        tag = "",
                        text = keyword
                    ) { text, isClick ->
                        if (isClick) {
                            resultString += text
                        }
                    }
                }

            }


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .background(neutralWhite),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement
                    .End
            ) {
                KeeplyButton(
                    onClick = {
                        Toast.makeText(
                            context,
                            "준비중입니다. 앱 종료 후 안내에 따라 진행해 주세요.",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    modifier = Modifier
                        .width(75.dp),
                    text = "취소",
                    buttonStyle = KeeplyButtonStyle.SECONDARY,
                    buttonSize = KeeplyButtonSize.SMALL
                )

                Spacer(modifier = Modifier.width(8.dp))

                KeeplyButton(
                    onClick = {
                        // TODO: 여기에서 /api/images 호출 원합니다 !!
                        // cachedImageId
                        // imageInsight 에 resultString

                        Toast.makeText(
                            context,
                            "준비중입니다. 앱 종료 후 안내에 따라 진행해 주세요.",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    modifier = Modifier
                        .width(75.dp),
                    enabled = true,
                    text = "이동",
                    buttonSize = KeeplyButtonSize.SMALL
                )
            }
        }
    }
}

@Composable
fun FolderTextList(
    modifier: Modifier = Modifier,
    tag: String,
    text: String,
    onClick: (String, Boolean) -> Unit
) {
    var isClick by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .background(if (isClick) KeeplyTheme.colors.neutral1000 else KeeplyTheme.colors.neutral300)
            .fillMaxWidth()
            .clickable {
                isClick = !isClick
                if (isClick) {
                    onClick(text, isClick)
                }
            }
            .padding(all = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        KeeplyText(
            modifier = Modifier
                .padding(start = 4.dp),
            text = text,
            style = KeeplyTheme.typography.button01Suit,
            color = if (isClick) KeeplyTheme.colors.neutralWhite else KeeplyTheme.colors.neutralBlack,
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


//@Preview(showBackground = true, heightDp = 750)
//@Composable
//private fun ScanScreenPreview() {
//    KeeplyTheme {
//        ScanAfterScreen(
//            textField = "",
//            textFieldMaxLength = 300,
//            onBack = { },
//            onSave = { },
//            onValueChange = { }
//        )
//    }
//}