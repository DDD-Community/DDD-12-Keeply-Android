package com.keeply.presentation.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.keeply.presentation.core.theme.KeeplyTheme
import com.keeply.presentation.core.theme.LocalColors

@Composable
fun FileTag(
    text: String,
    style: FileTagStyle,
    modifier: Modifier = Modifier,
    color: Color = KeeplyTheme.colors.orange400 // string으로 바꿔야..??
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(style.spacing)
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(
                    color = color,
                )
        )
        
        KeeplyText(
            text = text,
            style = style.textStyle,
            color = color
        )
    }
}

@Immutable
sealed interface FileTagStyle {
    data object Small: FileTagStyle
    data object Medium: FileTagStyle

    val textStyle: TextStyle
        @Composable
        get() = when (this) {
            Medium -> KeeplyTheme.typography.subtitle02
            Small -> KeeplyTheme.typography.subtitle03
        }
    
    val spacing: Dp
        get() = when (this) {
            Medium -> 8.dp
            Small -> 4.dp
        }
}

@Preview(showBackground = true)
@Composable
private fun FileTagAllStylesPreview() {
    KeeplyTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Small style
            Column {
                KeeplyText(
                    text = "Small Style",
                    style = KeeplyTheme.typography.caption01,
                    color = LocalColors.current.neutral600
                )
                Spacer(modifier = Modifier.height(8.dp))
                FileTag(
                    text = "Text",
                    style = FileTagStyle.Small
                )
            }
            
            // Medium style
            Column {
                KeeplyText(
                    text = "Medium Style",
                    style = KeeplyTheme.typography.caption01,
                    color = LocalColors.current.neutral600
                )
                Spacer(modifier = Modifier.height(8.dp))
                FileTag(
                    text = "Text",
                    style = FileTagStyle.Medium
                )
            }
        }
    }
}