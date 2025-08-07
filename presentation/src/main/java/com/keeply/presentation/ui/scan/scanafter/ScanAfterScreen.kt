package com.keeply.presentation.ui.scan.scanafter

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.keeply.presentation.core.theme.LocalColors
import com.keeply.presentation.core.theme.neutral100
import com.keeply.presentation.core.theme.neutralWhite
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import java.net.URLDecoder

@Composable
fun ScanAfterRoute(
    onBack: () -> Unit,
    onSave: () -> Unit,
    viewModel: ScanAfterViewModel = hiltViewModel()
) {
    val uiState by viewModel.collectAsState()
    val context = LocalContext.current

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is ScanAfterSideEffect.ShowError -> {
                Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
            }

            is ScanAfterSideEffect.NavigateToSuccess -> {
                onSave()
            }
        }
    }

    BackHandler { onBack.invoke() }

    ScanAfterScreen(
        uri = uiState.uri.toUri(),
        context = context,
        textField = uiState.textField,
        textFieldMaxLength = uiState.textFieldMaxLength,
        onBack = onBack,
        onSave = onSave,
        onValueChange = viewModel::onValueChange,
        onSaveClick = viewModel::onSaveClick,
        cachedImageId = uiState.cachedImageId,
        recommendedTags = uiState.recommendedTags,
        detectedText = uiState.detectedText,
        isLoading = uiState.isLoading
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
    onSaveClick: (String, Long) -> Unit,
    cachedImageId: String?,
    recommendedTags: List<String>? = null,
    detectedText: String?,
    isLoading: Boolean,

    onBack: () -> Unit,
    onSave: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    var isSheetVisible by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(neutral100) // #F4F4F4 해야되는데 없어서 임시
    ) {
        if (isSheetVisible) {
            SelectTextBottomSheet(
                modifier = Modifier,
                context = context,
                sheetState = sheetState,
                cachedImageId = cachedImageId,
                detectedText = detectedText ?: "추출된 텍스트가 없습니다.",
                onBack = {
                    scope.launch {
                        sheetState.hide()
                    }
                    isSheetVisible = false
                },
                onSaveClick = { text, folderId ->
                    scope.launch {
                        sheetState.hide()
                    }
                    isSheetVisible = false
                    onSaveClick(text, folderId)
                },
                isLoading = isLoading
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
    detectedText: String,
    onBack: () -> Unit,
    onSaveClick: (String, Long) -> Unit,
    isLoading: Boolean
) {
    if (cachedImageId.isNullOrBlank()) return // 추후수정


//    BackHandler { onBack() }

    // TODO: dragHandle 추가
    ModalBottomSheet(
        onDismissRequest = onBack,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        containerColor = neutralWhite,
        modifier = modifier,
        content = {
            SelectTextBottomSheetContent(
                context = context,
                detectedText = detectedText,
                onBackClick = onBack,
                onSaveClick = onSaveClick,
                isLoading = isLoading
            )
        }
    )
}

@Composable
fun SelectTextBottomSheetContent(
    context: Context = LocalContext.current,
    detectedText: String = "",
    onBackClick: () -> Unit = {},
    onSaveClick: (String, Long) -> Unit = { _, _ -> },
    isLoading: Boolean = false
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(neutralWhite)
            .padding(top = 24.dp, start = 16.dp, end = 16.dp, bottom = 32.dp)
    ) {
        val textList: List<String> = detectedText.split("\n")
        val selectedIndices = remember { mutableStateListOf<Int>() }

        // 헤더 (높이 고정)
        Column(
            modifier = Modifier
                .background(neutralWhite)
                .padding(bottom = 28.dp)
        ) {
            KeeplyText(
                text = "Text",
                style = KeeplyTheme.typography.header04,
            )

            KeeplyText(
                modifier = Modifier.padding(top = 6.dp),
                text = "스크린샷에서 추출한 문구 중\n기록에 활용할 문구를 선택해주세요.",
                style = KeeplyTheme.typography.body,
                color = LocalColors.current.neutral600
            )
        }

        // 추출된 텍스트 (스크롤)
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
        ) {
            repeat(textList.size) { index ->
                val keyword = textList[index]
                FolderTextList(
                    modifier = Modifier
                        .padding(vertical = 3.dp),
                    text = keyword
                ) { isSelected ->
                    if (isSelected) {
                        selectedIndices.remove(index)
                    } else {
                        selectedIndices.add(index)
                    }
                }
            }
        }

        // 하단 버튼 (높이 고정)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp)
                .background(neutralWhite),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            KeeplyButton(
                onClick = {
                    onBackClick()
                },
                modifier = Modifier.wrapContentWidth(),
                text = "돌아가기",
                buttonStyle = KeeplyButtonStyle.SECONDARY,
                buttonSize = KeeplyButtonSize.SMALL
            )

            Spacer(modifier = Modifier.width(8.dp))

            KeeplyButton(
                onClick = {
                    // TODO: 폴더 선택 기능 추가 필요, 현재는 기본값 1 사용
                    val selectedText = selectedIndices
                        .sorted().joinToString(separator = "\n") { textList[it] }
                    onSaveClick(selectedText, 1)
                },
                modifier = Modifier
                    .wrapContentWidth()
                    .widthIn(min = 75.dp),
                enabled = !isLoading,
                text = "다음",
                buttonSize = KeeplyButtonSize.SMALL
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


@Preview(widthDp = 360, heightDp = 600)
@Composable
private fun SelectTextBottomSheetPreview() {
    KeeplyTheme {
        SelectTextBottomSheetContent(
            detectedText = "하이하이\n다음말이다\n반갑소\n이러한 책 속의 인용들을 보며, 나는 좋은 문구를 기록만 해두는 경우가 많은데, 잘 활용하는 것도 중요하단 생각을 많이 했다."

        )
    }
}


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