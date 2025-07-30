package com.keeply.presentation.ui.folder

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.keeply.presentation.core.components.FolderIcon
import com.keeply.presentation.core.components.KeeplyIconButton
import com.keeply.presentation.core.components.KeeplyText
import com.keeply.presentation.core.theme.KeeplyTheme
import com.keeply.presentation.ui.folder.component.FolderCardItem

@Composable
fun FolderDisplay() {
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
            items(6) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    FolderCardItem(
                        modifier = Modifier
                            .padding(bottom = 12.dp)
                            .weight(1f)
                    )

                    FolderCardItem(
                        modifier = Modifier
                            .padding(bottom = 12.dp)
                            .weight(1f)
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