package com.keeply.presentation.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.keeply.domain.folder.model.Folder
import com.keeply.presentation.core.theme.KeeplyTheme
import com.keeply.presentation.core.theme.neutralWhite

@Composable
fun FolderList(
    modifier: Modifier = Modifier,
    folder: Folder
) {
    Row(
        modifier = modifier
            .background(neutralWhite)
            .fillMaxWidth()
            .padding(all = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FileTag(
            modifier = Modifier,
            text = folder.folderName,
            style = FileTagStyle.Small,
            color = Color(folder.color.toLong(16))
        )

        Box(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .width(1.dp)
                .height(10.dp)
                .background(KeeplyTheme.colors.neutral200)
        )

        Icon(
            modifier = Modifier
                .size(12.dp),
            painter = KeeplyTheme.icons.alignBox,
            contentDescription = "folder",
            tint = KeeplyTheme.colors.neutral500
        )

        KeeplyText(
            modifier = Modifier
                .padding(start = 4.dp),
            text = folder.imageCount.toString(),
            style = KeeplyTheme.typography.caption02,
            color = KeeplyTheme.colors.neutral500
        )
    }
}

@Preview
@Composable
fun FolderListPreview() {
    KeeplyTheme {
        FolderList(
            folder = Folder(
                folderId = 1,
                folderName = "독서",
                color = "FF7AB9F2",
                imageCount = 3,
                updatedAt = "0000"
            )
        )
    }
}
