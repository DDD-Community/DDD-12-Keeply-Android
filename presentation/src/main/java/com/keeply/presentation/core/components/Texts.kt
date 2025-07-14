package com.keeply.presentation.core.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.keeply.presentation.core.theme.KeeplyTheme
import com.keeply.presentation.core.theme.LocalColors

@Composable
fun KeeplyText(
    text: String,
    style: TextStyle,
    modifier: Modifier = Modifier,
    color: Color = LocalColors.current.neutralBlack,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    textAlign: TextAlign? = null,
    verticalAlignment: LineHeightStyle.Alignment = LineHeightStyle.Alignment.Center,
    textDecoration: TextDecoration? = null,
) {
    val lineHeight =
        if (style.lineHeight != TextUnit.Unspecified) style.getTextLineHeight()
        else TextUnit.Unspecified

    val textStyle = style.copy(
        lineHeightStyle = LineHeightStyle(
            alignment = verticalAlignment,
            trim = LineHeightStyle.Trim.None
        )
    )

    Text(
        text = text,
        modifier = modifier,
        style = textStyle,
        color = color,
        maxLines = maxLines,
        overflow = overflow,
        textAlign = textAlign,
        textDecoration = textDecoration,
        fontSize = style.getTextFontSize(),
        lineHeight = lineHeight
    )
}

@Composable
fun KeeplyText(
    text: AnnotatedString,
    style: TextStyle,
    modifier: Modifier = Modifier,
    color: Color = LocalColors.current.neutralBlack,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    textAlign: TextAlign? = null,
    verticalAlignment: LineHeightStyle.Alignment = LineHeightStyle.Alignment.Center,
    textDecoration: TextDecoration? = null,
) {
    val lineHeight =
        if (style.lineHeight != TextUnit.Unspecified) style.getTextLineHeight()
        else TextUnit.Unspecified

    val textStyle = style.copy(
        lineHeightStyle = LineHeightStyle(
            alignment = verticalAlignment,
            trim = LineHeightStyle.Trim.None
        )
    )

    Text(
        text = text,
        modifier = modifier,
        style = textStyle,
        color = color,
        maxLines = maxLines,
        overflow = overflow,
        textAlign = textAlign,
        textDecoration = textDecoration,
        fontSize = style.getTextFontSize(),
        lineHeight = lineHeight
    )
}


@Composable
fun Dp.dpToTextUnit() = with(LocalDensity.current) { toSp() }

@Composable
fun TextStyle.getTextFontSize(): TextUnit {
    return fontSize.value.dp.dpToTextUnit()
}

@Composable
fun TextStyle.getTextLineHeight(): TextUnit {
    return lineHeight.value.dp.dpToTextUnit()
}

@Preview(showBackground = true)
@Composable
private fun KeeplyTextPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        KeeplyText(text = "header01", style = KeeplyTheme.typography.header01)
        KeeplyText(text = "header02", style = KeeplyTheme.typography.header02)
        KeeplyText(text = "header03", style = KeeplyTheme.typography.header03)
        KeeplyText(text = "header04", style = KeeplyTheme.typography.header04)
        KeeplyText(text = "header05", style = KeeplyTheme.typography.header05)
        KeeplyText(text = "header06", style = KeeplyTheme.typography.header06)
        KeeplyText(text = "subtitle01", style = KeeplyTheme.typography.subtitle01)
        KeeplyText(text = "subtitle02", style = KeeplyTheme.typography.subtitle02)
        KeeplyText(text = "subtitle03", style = KeeplyTheme.typography.subtitle03)
        KeeplyText(text = "body", style = KeeplyTheme.typography.body)
        KeeplyText(text = "caption01", style = KeeplyTheme.typography.caption01)
        KeeplyText(text = "caption02", style = KeeplyTheme.typography.caption02)
        KeeplyText(text = "button01Altform", style = KeeplyTheme.typography.button01Altform)
        KeeplyText(text = "button02Altform", style = KeeplyTheme.typography.button02Altform)
        KeeplyText(text = "button01Suit", style = KeeplyTheme.typography.button01Suit)
        KeeplyText(text = "button02Suit", style = KeeplyTheme.typography.button02Suit)
    }
}
