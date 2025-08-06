package com.keeply.presentation.ui.folder.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.keeply.domain.folder.model.Folder
import com.keeply.presentation.core.components.FolderIcon
import com.keeply.presentation.core.components.KeeplyText
import com.keeply.presentation.core.components.colorBar.FolderColor
import com.keeply.presentation.core.components.colorBar.toComposeColor
import com.keeply.presentation.core.components.colorBar.toHexString
import com.keeply.presentation.core.theme.KeeplyTheme
import com.keeply.presentation.extend.formatDate
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun FolderCardItem(
    modifier: Modifier = Modifier,
    folder: Folder,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(KeeplyTheme.colors.neutralWhite)
            .clickable { onClick() }
            .padding(12.dp)
    ) {
        val folderColor = folder.color.let {
            FolderColor.entries.firstOrNull { color ->
                color.toHexString() == it
            }
        } ?: FolderColor.ORANGE
        
        FolderIcon(
            iconModifier = Modifier
                .width(56.dp)
                .height(42.dp),
            tint = folderColor.toComposeColor()
        )

        KeeplyText(
            modifier = Modifier
                .padding(top = 18.dp),
            text = folder.folderName,
            style = KeeplyTheme.typography.subtitle02,
            maxLines = 1
        )

        Row(
            modifier = Modifier
                .padding(top = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            KeeplyText(
                text = folder.updatedAt.formatDate(),
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
                text = folder.imageCount.toString(),
                style = KeeplyTheme.typography.caption02,
                color = KeeplyTheme.colors.neutral600
            )
        }
    }
}