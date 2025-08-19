package com.keeply.presentation.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.keeply.domain.folder.model.Folder
import com.keeply.presentation.core.theme.KeeplyTheme

@Composable
fun FolderList(
    modifier: Modifier = Modifier,
    folder: Folder,
    isSelected: Boolean = false
) {
    val (backgroundColor, borderColor) = if (isSelected) {
        KeeplyTheme.colors.neutralWhite to KeeplyTheme.colors.orange400
    } else {
        KeeplyTheme.colors.neutral200 to KeeplyTheme.colors.neutral200
    }

    Row(
        modifier = modifier
            .border(1.dp, borderColor, RoundedCornerShape(2.dp))
            .background(backgroundColor, RoundedCornerShape(2.dp))
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
        Column {
            FolderList(
                folder = Folder(
                    folderId = 1,
                    folderName = "독서",
                    color = "FF7AB9F2",
                    imageCount = 3,
                    updatedAt = "0000"
                ),
                isSelected = true
            )

            FolderList(
                modifier = Modifier.padding(top = 10.dp),
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
}
