package com.keeply.presentation.core.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.keeply.presentation.core.theme.KeeplyTheme

@Composable
fun KeeplyTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    enabled: Boolean = true,
    helpIcon: Painter? = null,
    helpText: String = "",
    helpColor: Color = KeeplyTheme.colors.error,
    showClearButton: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    singleLine: Boolean = true,
    maxLines: Int = 1,
) {
    val clearButtonVisibility = showClearButton && value.isNotEmpty()
    val paddingModifier = if (clearButtonVisibility)
        Modifier.padding(start = 8.dp, end = 4.dp)
    else
        Modifier.padding(horizontal = 8.dp)

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .border(
                    width = 1.dp,
                    color = KeeplyTheme.colors.neutral200,
                    shape = RoundedCornerShape(4.dp)
                )
                .then(paddingModifier),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Box(
                modifier = Modifier.weight(1f)
            ) {
                if (value.isEmpty()) {
                    KeeplyText(
                        text = placeholder,
                        style = KeeplyTheme.typography.body,
                        color = KeeplyTheme.colors.neutral600
                    )
                }

                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    modifier = Modifier
                        .fillMaxWidth(),
                    enabled = enabled,
                    textStyle = KeeplyTheme.typography.button01Suit.copy(
                        color = if (enabled)
                            KeeplyTheme.colors.neutralBlack
                        else
                            KeeplyTheme.colors.neutral300
                    ),
                    cursorBrush = SolidColor(KeeplyTheme.colors.orange400),
                    visualTransformation = visualTransformation,
                    keyboardOptions = keyboardOptions,
                    singleLine = singleLine,
                    maxLines = maxLines
                )
            }

            if (clearButtonVisibility) {
                IconButton(
                    onClick = { onValueChange("") },
                    modifier = Modifier.size(24.dp),
                ) {
                    Icon(
                        modifier = Modifier
                            .size(16.dp),
                        painter = KeeplyTheme.icons.closeFilled,
                        contentDescription = "Clear",
                        tint = KeeplyTheme.colors.neutral300
                    )
                }
            }
        }
        
        if (helpText.isNotEmpty() || helpIcon != null) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                helpIcon?.let {
                    Icon(
                        painter = helpIcon,
                        contentDescription = null,
                        modifier = Modifier.size(12.dp),
                        tint = helpColor
                    )
                }
                KeeplyText(
                    text = helpText,
                    style = KeeplyTheme.typography.caption02,
                    color = helpColor
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "KeeplyTextField - All States")
@Composable
private fun KeeplyTextFieldPreview() {
    KeeplyTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 1. Default state
            KeeplyTextField(
                value = "",
                onValueChange = { },
                placeholder = "Input"
            )
            
            // 2. Focused state
            KeeplyTextField(
                value = "",
                onValueChange = { },
                placeholder = "Input",
            )
            
            // 3. Filled state
            KeeplyTextField(
                value = "Input",
                onValueChange = { },
                placeholder = "Input"
            )
            
            // 4. Disabled state
            KeeplyTextField(
                value = "",
                onValueChange = { },
                placeholder = "Input",
                enabled = false
            )
            
            // 5. With trailing icon and error
            KeeplyTextField(
                value = "Input",
                onValueChange = { },
                placeholder = "Input",
                helpText = "최대 20자까지 입력 가능합니다.",
                showClearButton = true
            )
        }
    }
}