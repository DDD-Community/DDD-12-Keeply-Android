package com.keeply.presentation.ui.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.keeply.presentation.core.components.FileTag
import com.keeply.presentation.core.components.FileTagStyle
import com.keeply.presentation.core.components.ImageFrame
import com.keeply.presentation.core.components.KeeplyText
import com.keeply.presentation.core.theme.KeeplyTheme

@Composable
fun HomeKeeplyScreenshotItem(
    modifier: Modifier = Modifier,
    painter: Painter? = null,
    tag: String = "",
    date: String = "",
    insight: String = ""
) {
    Row(
        modifier = modifier
    ) {
        ImageFrame(
            modifier = Modifier
                .width(70.dp),
            painter = painter ?: ColorPainter(KeeplyTheme.colors.neutral200)
        )

        Column(
            modifier = Modifier
                .padding(start = 20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                FileTag(
                    modifier = Modifier
                        .weight(1f),
                    text = tag,
                    style = FileTagStyle.Small
                )

                KeeplyText(
                    text = date,
                    style = KeeplyTheme.typography.subtitle03,
                    color = KeeplyTheme.colors.neutral600
                )
            }

            KeeplyText(
                text = insight,
                style = KeeplyTheme.typography.body,
                color = KeeplyTheme.colors.neutral900,
                maxLines = 3
            )
        }
    }
}

@Preview
@Composable
fun HomeKeeplyScreenshotItemPreview() {
    KeeplyTheme {
        HomeKeeplyScreenshotItem()
    }
}

