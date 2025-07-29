package com.keeply.presentation.ui.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.keeply.presentation.core.components.ImageFrameList
import com.keeply.presentation.core.components.KeeplyText
import com.keeply.presentation.core.theme.KeeplyTheme

@Composable
fun HomeUncategorizedCard(
    modifier: Modifier = Modifier,
    title: String,
    count: Int
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(KeeplyTheme.colors.neutralWhite)
            .padding(
                top = 8.dp,
                start = 8.dp,
                end = 8.dp,
                bottom = 12.dp
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(KeeplyTheme.colors.neutral100)
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            ImageFrameList(
                cardWidth = 69.dp
            )
        }

        Row(
            modifier = Modifier
                .padding(
                    top = 12.dp,
                    start = 5.dp,
                    end = 5.dp
                )
        ) {
            KeeplyText(
                modifier = Modifier
                    .weight(1f),
                text = title,
                style = KeeplyTheme.typography.subtitle02,
                color = KeeplyTheme.colors.neutral800,
            )

            Icon(
                modifier = Modifier
                    .size(16.dp),
                painter  = KeeplyTheme.icons.arrowRight,
                contentDescription = "chevron right"
            )
        }

        KeeplyText(
            modifier = Modifier
                .padding(
                    top = 4.dp,
                    start = 5.dp
                ),
            text = "$count",
            style = KeeplyTheme.typography.header03,
            color = KeeplyTheme.colors.neutral900,
        )
    }
}

@Preview
@Composable
fun HomeUncategorizedCardPreview() {
    KeeplyTheme {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            HomeUncategorizedCard(
                modifier = Modifier
                    .weight(1f),
                title = "Uncategorized",
                count = 1
            )

            HomeUncategorizedCard(
                modifier = Modifier
                    .weight(1f),
                title = "Uncategorized",
                count = 1
            )
        }

    }
}