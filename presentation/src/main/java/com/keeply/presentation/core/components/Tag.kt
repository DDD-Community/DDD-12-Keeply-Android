package com.keeply.presentation.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.keeply.presentation.core.theme.KeeplyTheme

@Composable
fun Tag(
    label: String,
    checked: Boolean,
    modifier: Modifier = Modifier,
    onCheckedChange: (Boolean) -> Unit,
) {
    val backgroundColor = if (checked)
        KeeplyTheme.colors.neutral200
    else
        Color.Transparent

    val textColor = if (checked)
        KeeplyTheme.colors.neutral900
    else
        KeeplyTheme.colors.neutral500

    KeeplyText(
        text = label,
        style = KeeplyTheme.typography.button01Suit,
        color = textColor,
        modifier = modifier
            .clip(RoundedCornerShape(100))
            .clickable { onCheckedChange(!checked) }
            .background(backgroundColor)
            .padding(horizontal = 10.dp, vertical = 6.dp)
    )
}

@Preview(showBackground = true)
@Composable
private fun TagInteractivePreview() {
    KeeplyTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            var checked1 by remember { mutableStateOf(false) }
            var checked2 by remember { mutableStateOf(true) }

            Column {
                KeeplyText(
                    text = "Click to toggle",
                    style = KeeplyTheme.typography.caption01,
                    color = KeeplyTheme.colors.neutral600
                )
                Row(
                    modifier = Modifier.padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Tag(
                        label = "label",
                        checked = checked1,
                        onCheckedChange = { checked1 = it }
                    )
                    Tag(
                        label = "label",
                        checked = checked2,
                        onCheckedChange = { checked2 = it }
                    )
                }
            }
        }
    }
}