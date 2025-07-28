package com.keeply.presentation.ui.scan

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import com.keeply.presentation.core.components.KeeplyAppBar
import com.keeply.presentation.core.components.KeeplyText
import com.keeply.presentation.core.theme.KeeplyTheme
import com.keeply.presentation.core.theme.neutral100

@Composable
fun ScanRoute() {
    ScanScreen()
}

@Composable
fun ScanScreen() {
    ScreenshotScreen()
}

@Composable
fun ScreenshotScreen(viewModel: LocalScreenshotViewModel = hiltViewModel()) {
    val lazyPagingItems = viewModel.screenshots.collectAsLazyPagingItems()

    // TODO: 화면 재진입 시 스샷목록 새로고침

    ScreenshotFrame(
        count = lazyPagingItems.itemCount
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxSize()
        ) {
            items(lazyPagingItems.itemCount) { index ->
                lazyPagingItems[index]?.let { screenshot ->
                    // TODO: imageFrame 사용으로 변환
                    // TODO: 스크린샷 선택, 뒤로가기 버튼 활성화
                    Image(
                        painter = rememberAsyncImagePainter(screenshot.uri),
                        contentDescription = null,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .padding(1.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ScreenshotFrame(
    modifier: Modifier = Modifier,
    count: Int = 0,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .background(neutral100)
    ) {
        KeeplyAppBar(
            customTitleContent = {
                Row(
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .align(Alignment.CenterStart),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    KeeplyText(
                        text = "Screenshots",
                        style = KeeplyTheme.typography.header03,
                    )

                    KeeplyText(
                        text = "$count",
                        style = KeeplyTheme.typography.header04,
                        color = KeeplyTheme.colors.neutral500
                    )
                }
            },
            leadingIcon = {
                Icon(
                    painter = KeeplyTheme.icons.chevronLeft,
                    contentDescription = "Back",
                    tint = KeeplyTheme.colors.neutralBlack
                )
            },
        )

        content()
    }
}

@Preview(showBackground = true)
@Composable
private fun ScanScreenPreview() {
    KeeplyTheme {
        ScreenshotFrame {
            KeeplyText(
                text = "Contents",
                style = KeeplyTheme.typography.header03,
            )
        }
    }
}