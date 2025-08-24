package com.keeply.presentation.core.components

import androidx.annotation.Keep
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.keeply.presentation.core.theme.KeeplyTheme

@Composable
fun CropBar(
    modifier: Modifier = Modifier,
    onClickCancel: () -> Unit = {},
    onClickConfirm: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .background(KeeplyTheme.colors.neutralBlack)
            .height(97.dp)
            .fillMaxWidth()
            .padding(
                bottom = 32.dp,
                start = 16.dp,
                end = 16.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .clip(CircleShape)
                .clickable {
                    onClickCancel()
                }
                .background(KeeplyTheme.colors.neutral900)
                .size(40.dp)
                .padding(8.dp),
            painter = KeeplyTheme.icons.chevronLeft,
            contentDescription = "cancel",
            tint = KeeplyTheme.colors.neutral300
        )

        KeeplyText(
            modifier = Modifier
                .weight(1f),
            text = "Crop",
            style = KeeplyTheme.typography.header04,
            textAlign = TextAlign.Center,
            color = KeeplyTheme.colors.neutralWhite
        )

        Icon(
            modifier = Modifier
                .clip(CircleShape)
                .clickable {
                    onClickConfirm()
                }
                .background(KeeplyTheme.colors.orange400)
                .size(40.dp)
                .padding(8.dp),
            painter = KeeplyTheme.icons.checkmark,
            contentDescription = "cancel",
            tint = KeeplyTheme.colors.neutralWhite
        )
    }
}

@Preview
@Composable
fun CropBarPreview() {
    KeeplyTheme {
        CropBar()
    }
}