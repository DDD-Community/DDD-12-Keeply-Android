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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.keeply.domain.folder.model.FolderImage
import com.keeply.presentation.R
import com.keeply.presentation.core.components.KeeplyIconButton
import com.keeply.presentation.core.components.KeeplyText
import com.keeply.presentation.core.components.Tag
import com.keeply.presentation.core.theme.KeeplyTheme
import com.keeply.presentation.ui.folder.component.UncategorizedCardItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun UncategorizedDisplay(
    isExpireToday: Boolean,
    images: ImmutableList<FolderImage>,
    updateExpireToday: (Boolean) -> Unit = {},
    isLoading: Boolean = false,
) {
    val contentImages = images.filter { if (isExpireToday) it.daysUntilDeletion == 0 else true }
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
                text = stringResource(R.string.folder_uncategorized_count, contentImages.size),
                style = KeeplyTheme.typography.subtitle02,
                color = KeeplyTheme.colors.neutral600
            )

            KeeplyIconButton(
                painter = KeeplyTheme.icons.add,
            )
        }

        val chunkedList = contentImages.chunked(3)

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
                        label = stringResource(R.string.folder_uncategorized_view_all),
                        checked = isExpireToday.not(),
                        onCheckedChange = {
                            updateExpireToday(false)
                        }
                    )

                    Tag(
                        label = stringResource(R.string.folder_uncategorized_expire_today),
                        checked = isExpireToday,
                        onCheckedChange = {
                            updateExpireToday(true)
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
                    rowItems.forEach { image ->
                        UncategorizedCardItem(
                            modifier = Modifier
                                .weight(1f)
                                .wrapContentHeight(),
                            imageUrl = image.presignedUrl,
                            daysUntilDeletion = image.daysUntilDeletion
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
        UncategorizedDisplay(
            isExpireToday = false,
            images = persistentListOf()
        )
    }
}