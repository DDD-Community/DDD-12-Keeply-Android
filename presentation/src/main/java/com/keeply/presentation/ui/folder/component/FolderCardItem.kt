package com.keeply.presentation.ui.folder.component

import androidx.compose.foundation.background
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
import com.keeply.presentation.core.components.FolderIcon
import com.keeply.presentation.core.components.KeeplyText
import com.keeply.presentation.core.theme.KeeplyTheme

@Composable
fun FolderCardItem(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(KeeplyTheme.colors.neutralWhite)
            .padding(12.dp)
    ) {
        FolderIcon(
            iconModifier = Modifier
                .width(56.dp)
                .height(42.dp),
            tint = KeeplyTheme.colors.orange400
        )

        KeeplyText(
            modifier = Modifier
                .padding(top = 18.dp),
            text = "Team DDD_안칠수",
            style = KeeplyTheme.typography.subtitle02,
            maxLines = 1
        )

        Row(
            modifier = Modifier
                .padding(top = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            KeeplyText(
                text = "2025.06.19",
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
                text = "5",
                style = KeeplyTheme.typography.caption02,
                color = KeeplyTheme.colors.neutral600
            )
        }
    }
}