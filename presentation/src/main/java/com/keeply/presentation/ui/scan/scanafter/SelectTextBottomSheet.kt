package com.keeply.presentation.ui.scan.scanafter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.keeply.presentation.core.components.KeeplyButton
import com.keeply.presentation.core.components.KeeplyButtonSize
import com.keeply.presentation.core.components.KeeplyButtonStyle
import com.keeply.presentation.core.components.KeeplyModalBottomSheet
import com.keeply.presentation.core.components.KeeplyText
import com.keeply.presentation.core.theme.KeeplyTheme
import com.keeply.presentation.core.theme.LocalColors
import com.keeply.presentation.core.theme.neutralWhite

@Composable
fun SelectTextBottomSheet(
    textList: List<String>,
    onBack: () -> Unit,
    onNextClick: (List<Int>) -> Unit,
    isLoading: Boolean
) {
    // TODO: UI수정 (바텀시트 높이 적용, 하단버튼 네비게이션바 위에 고정)

    val selectedIndices = remember { mutableStateListOf<Int>() }

    KeeplyModalBottomSheet(
        onDismissRequest = { onNextClick(selectedIndices) },
        skipPartiallyExpanded = false
    ) {
        SelectTextBottomSheetContent(
            textList = textList,
            onBackClick = onBack,
            onNextClick = { onNextClick(selectedIndices) },
            onClickItem = { index, isSelected ->
                if (isSelected) {
                    selectedIndices.add(index)
                } else {
                    selectedIndices.remove(index)
                }
            },
            isLoading = isLoading
        )
    }
}

@Composable
fun SelectTextBottomSheetContent(
    textList: List<String>,
    onBackClick: () -> Unit = {},
    onNextClick: () -> Unit = {},
    onClickItem: (Int, Boolean) -> Unit = { _, _ -> },
    isLoading: Boolean = false
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .heightIn(min = 417.dp)
            .background(neutralWhite)
            .padding(top = 24.dp, start = 16.dp, end = 16.dp, bottom = 32.dp)
    ) {
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
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .heightIn(min = 168.dp)
        ) {
            itemsIndexed(textList) { index, keyword ->
                FolderTextList(
                    modifier = Modifier
                        .padding(vertical = 3.dp),
                    text = keyword
                ) { isSelected ->
                    onClickItem(index, isSelected)
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
                onClick = onNextClick,
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

@Preview(widthDp = 360, heightDp = 300)
@Composable
private fun SelectTextBottomSheetPreview() {
    KeeplyTheme {
        SelectTextBottomSheetContent(
            textList = listOf(
                "하이",
                "하이2",
                "이러한 책 속의 인용들을 보며, 나는 좋은 문구를 기록만 해두는 경우가 많은데, 잘 활용하는 것도 중요하단 생각을 많이 했다.",
                "이러한 책 속의 인용들을 보며, 나는 좋은 문구를 기록만 해두는 경우가 많은데, 잘 활용하는 것도 중요하단 생각을 많이 했다.",
                "이러한 책 속의 인용들을 보며, 나는 좋은 문구를 기록만 해두는 경우가 많은데, 잘 활용하는 것도 중요하단 생각을 많이 했다.",
                "이러한 책 속의 인용들을 보며, 나는 좋은 문구를 기록만 해두는 경우가 많은데, 잘 활용하는 것도 중요하단 생각을 많이 했다."
            )
        )
    }
}