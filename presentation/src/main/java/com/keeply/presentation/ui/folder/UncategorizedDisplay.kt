package com.keeply.presentation.ui.folder

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.keeply.presentation.core.components.KeeplyIconButton
import com.keeply.presentation.core.components.KeeplyText
import com.keeply.presentation.core.components.Tag
import com.keeply.presentation.core.theme.KeeplyTheme
import com.keeply.presentation.ui.folder.component.UncategorizedCardItem

@Composable
fun UncategorizedDisplay() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 12.dp,
                    horizontal = 16.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            KeeplyText(
                modifier = Modifier
                    .weight(1f),
                text = "999개",
                style = KeeplyTheme.typography.subtitle02,
                color = KeeplyTheme.colors.neutral600
            )

            KeeplyIconButton(
                painter = KeeplyTheme.icons.add,
            )
        }

        val folderList = List(100) { it } // 예시 리스트
        val chunkedList = folderList.chunked(3)

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(
                start = 12.dp,
                end = 12.dp,
                bottom = 112.dp
            )
        ) {
            item {
                Row(
                    modifier = Modifier
                        .padding(bottom = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    var labelCheck by remember { mutableStateOf(true) }

                    Tag(
                        label = "전체보기",
                        checked = labelCheck,
                        onCheckedChange = {
                            labelCheck = true
                        }
                    )

                    Tag(
                        label = "오늘만료",
                        checked = labelCheck.not(),
                        onCheckedChange = {
                            labelCheck = false
                        }
                    )
                }
            }

            items(chunkedList.size) { index ->
                val rowItems = chunkedList[index]

                Row(
                    modifier = Modifier
                        .padding(
                            start = 4.dp,
                            end = 4.dp,
                            bottom = 10.dp
                        )
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    rowItems.forEach { item ->
                        UncategorizedCardItem(
                            modifier = Modifier
                                .weight(1f)
                                .wrapContentHeight(),
                        )
                    }

                    // 3개가 되지 않을 때 빈 공간 추가
                    repeat(3 - rowItems.size) {
                        Spacer(
                            modifier = Modifier
                                .weight(1f)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UncategorizedDisplayPreview() {
    KeeplyTheme {
        UncategorizedDisplay()
    }
}