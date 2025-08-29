package com.keeply.presentation.ui.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.keeply.domain.home.model.HomeFolder
import com.keeply.presentation.core.components.FolderIcon
import com.keeply.presentation.core.components.KeeplyText
import com.keeply.presentation.core.components.colorBar.FolderColor
import com.keeply.presentation.core.components.colorBar.toComposeColor
import com.keeply.presentation.core.components.colorBar.toHexString
import com.keeply.presentation.core.theme.KeeplyTheme
import com.keeply.presentation.extend.formatDate

@Composable
fun HomeKeeplyFolderItem(
    modifier: Modifier = Modifier,
    homeFolder: HomeFolder,
    onClickSelectedFolder: (String, String, String) -> Unit = { _, _, _ -> }
) {
    val folderColor = homeFolder.color.let {
        FolderColor.entries.firstOrNull { color ->
            color.toHexString() == it
        }
    } ?: FolderColor.ORANGE

    Row(
        modifier = modifier
            .clickable { onClickSelectedFolder(homeFolder.folderId.toString(), homeFolder.folderName, homeFolder.color) }
    ) {
        FolderIcon(
            iconModifier = Modifier
                .width(56.dp)
                .height(42.dp),
            tint = folderColor.toComposeColor()
        )

        Column(
            modifier = Modifier
                .padding(start = 20.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            KeeplyText(
                text = homeFolder.folderName,
                style = KeeplyTheme.typography.subtitle02,
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                KeeplyText(
                    text = homeFolder.updatedAt.formatDate(),
                    style = KeeplyTheme.typography.caption02,
                    color = KeeplyTheme.colors.neutral600
                )

                Box(
                    modifier = Modifier
                        .padding(horizontal = 6.dp)
                        .width(1.dp)
                        .height(10.dp)
                        .background(KeeplyTheme.colors.neutral300)
                )

                Icon(
                    modifier = Modifier
                        .size(12.dp),
                    painter = KeeplyTheme.icons.alignBox,
                    contentDescription = "folder",
                    tint = KeeplyTheme.colors.neutral600
                )

                KeeplyText(
                    modifier = Modifier
                        .padding(start = 2.dp),
                    text = homeFolder.imageCount.toString(),
                    style = KeeplyTheme.typography.caption02,
                    color = KeeplyTheme.colors.neutral600
                )
            }
        }
    }
}

@Preview
@Composable
fun HomeKeeplyFolderItemPreview() {
    KeeplyTheme {
        HomeKeeplyFolderItem(
            homeFolder = HomeFolder(
                folderId = 0,
                folderName = "위시리스트",
                updatedAt = "2025-08-16T14:52:40",
                imageCount = 5,
                color = ""
            )
        )
    }
}

