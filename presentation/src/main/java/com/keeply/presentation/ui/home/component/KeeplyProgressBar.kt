package com.keeply.presentation.ui.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.keeply.presentation.core.components.KeeplyText
import com.keeply.presentation.core.theme.KeeplyTheme
import kotlin.math.roundToInt

@Composable
fun KeeplyProgressBar(
    modifier: Modifier = Modifier,
    currentLength: Int,
    maxLength: Int = 300,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(KeeplyTheme.colors.neutralWhite)
            .padding(14.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            KeeplyText(
                text = "저장된 이미지",
                style = KeeplyTheme.typography.subtitle03,
                color = KeeplyTheme.colors.neutral600
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val scaledGauge = scaledGauge(maxLength, currentLength)
                repeat(40) {
                    ProgressBarGaugeItem(
                        isActive = it <= scaledGauge - 1
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.Bottom),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            KeeplyText(
                text = currentLength.toString(),
                style = KeeplyTheme.typography.header03,
            )

            KeeplyText(
                text = "/ $maxLength",
                style = KeeplyTheme.typography.header06,
                color = KeeplyTheme.colors.neutral400
            )
        }
    }
}

private fun scaledGauge(
    maxLength: Int,
    currentLength: Int,
    scale: Int = 40
) = ((currentLength.toDouble() / maxLength) * scale).roundToInt()


@Composable
private fun ProgressBarGaugeItem(
    isActive: Boolean
) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .width(2.dp)
            .height(15.dp)
            .background(
                if (isActive) KeeplyTheme.colors.orange400
                else KeeplyTheme.colors.neutral200
            )
    )
}

@Preview
@Composable
fun KeeplyProgressBarPreview() {
    KeeplyTheme {
        KeeplyProgressBar(
            currentLength = 150
        )
    }
}