package com.keeply.presentation.core.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.SolidColor
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
    errorMessage: String = "",
    isReset: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    singleLine: Boolean = true,
    maxLines: Int = 1,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = KeeplyTheme.colors.neutral200,
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
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

            if (isReset) {
                IconButton(
                    onClick = { onValueChange("") },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        painter = KeeplyTheme.icons.closeFilled,
                        contentDescription = "Clear",
                        tint = KeeplyTheme.colors.neutral300
                    )
                }
            }
        }
        
        if (errorMessage.isNotEmpty()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    painter = KeeplyTheme.icons.warningFilled,
                    contentDescription = "Error",
                    modifier = Modifier.size(12.dp),
                    tint = KeeplyTheme.colors.error
                )
                KeeplyText(
                    text = errorMessage,
                    style = KeeplyTheme.typography.caption02,
                    color = KeeplyTheme.colors.error
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
                errorMessage = "최대 20자까지 입력 가능합니다.",
                isReset = true
            )
        }
    }
}