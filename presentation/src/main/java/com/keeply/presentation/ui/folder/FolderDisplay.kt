package com.keeply.presentation.ui.folder

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.keeply.presentation.R
import com.keeply.presentation.core.components.KeeplyButton
import com.keeply.presentation.core.components.KeeplyButtonSize
import com.keeply.presentation.core.components.KeeplyIconButton
import com.keeply.presentation.core.components.KeeplyText
import com.keeply.presentation.core.theme.KeeplyTheme
import com.keeply.presentation.ui.folder.component.FolderCardItem

@Composable
fun FolderDisplay(
    onNavigateToAddFolder: () -> Unit = {}
) {
    var isNotEmpty by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (isNotEmpty) {
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

            val folderList = List(11) { it } // 예시 리스트
            val chunkedList = folderList.chunked(2)
            
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                contentPadding = PaddingValues(
                    top = 4.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 112.dp
                )
            ) {
                items(chunkedList.size) { index ->
                    val rowItems = chunkedList[index]
                    
                    Row(
                        modifier = Modifier
                            .padding(bottom = 12.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        rowItems.forEach { item ->
                            FolderCardItem(
                                modifier = Modifier
                                    .weight(1f)
                            )
                        }
                        
                        if (rowItems.size == 1) {
                            Spacer(
                                modifier = Modifier
                                    .weight(1f)
                            )
                        }
                    }
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lottie_add_folder))
                    val progress by animateLottieCompositionAsState(
                        composition = composition,
                        iterations = LottieConstants.IterateForever
                    )

                    LottieAnimation(
                        composition = composition,
                        progress = { progress }
                    )

                    KeeplyText(
                        modifier = Modifier
                            .padding(top = 12.dp),
                        text = "새로운 폴더가 필요해요.\n폴더를 추가해주세요.",
                        style = KeeplyTheme.typography.button01Suit,
                        color = KeeplyTheme.colors.neutral500
                    )

                    KeeplyButton(
                        modifier = Modifier
                            .padding(top = 28.dp)
                            .width(198.dp),
                        text = "폴더 추가",
                        icon = KeeplyTheme.icons.add,
                        buttonSize = KeeplyButtonSize.SMALL,
                        onClick = onNavigateToAddFolder
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FolderDisplayPreview() {
    KeeplyTheme {
        FolderDisplay()
    }
}