package com.keeply.presentation.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.keeply.presentation.R
import com.keeply.presentation.core.theme.KeeplyTheme

@Composable
fun ScanBar(
    modifier: Modifier = Modifier,
    onClickCrop: () -> Unit = {},
    onClickScan: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(100))
            .background(KeeplyTheme.colors.neutralBlack)
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Icon(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .clickable { onClickCrop() }
                .background(KeeplyTheme.colors.neutralWhite)
                .padding(16.dp),
            painter = KeeplyTheme.icons.crop,
            contentDescription = "crop",
        )

        Row(
            modifier = Modifier
                .height(56.dp)
                .clip(CircleShape)
                .clickable { onClickScan() }
                .background(KeeplyTheme.colors.orange400)
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                modifier = Modifier
                    .size(24.dp),
                painter = KeeplyTheme.icons.scanAlt,
                contentDescription = "scan",
                tint = KeeplyTheme.colors.neutralWhite
            )

            KeeplyText(
                text = stringResource(R.string.scan),
                style = KeeplyTheme.typography.header05,
                color = KeeplyTheme.colors.neutralWhite
            )
        }
    }
}

@Preview
@Composable
fun ScanBarPreview() {
    KeeplyTheme {
        ScanBar()
    }
}