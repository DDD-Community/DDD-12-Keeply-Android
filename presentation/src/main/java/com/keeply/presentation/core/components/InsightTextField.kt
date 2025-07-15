package com.keeply.presentation.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.keeply.presentation.core.theme.KeeplyTheme
import com.keeply.presentation.core.theme.LocalColors

@Composable
fun InsightTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    maxLength: Int = 300
) {
    Column (
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = KeeplyTheme.colors.neutral200,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(16.dp)
    ) {
        Box {
            if (value.isEmpty()) {
                KeeplyText(
                    text = placeholder,
                    style = KeeplyTheme.typography.body,
                    color = LocalColors.current.neutral500
                )
            }

            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(
                        min = 82.dp
                    ),
                value = value,
                onValueChange = { newText ->
                    if (newText.length <= maxLength) {
                        onValueChange(newText)
                    }
                },
                textStyle = KeeplyTheme.typography.body,
                cursorBrush = SolidColor(Color.Black),
            )
        }

        KeeplyText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            text = "${value.length}/$maxLength",
            style = KeeplyTheme.typography.caption02,
            color = LocalColors.current.neutral500,
            textAlign = TextAlign.End
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun InsightTextFieldPreview() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                KeeplyTheme.colors.folderBlue
            ).padding(16.dp)
    ) {
        var text by remember { mutableStateOf("ㄹㅁ아닣;ㅁㅇㄴㅎ" +
                "멍니ㅏㄹ;ㅓㅁㅇㄴ라ㅓㅁㅇ니;ㄹ" +
                "ㅁㄴ어리ㅏ;ㅇㅁ널;ㅣㅏㅁ너" +
                ";ㅏㅣ먼ㅇㄹ;ㅇ마널;ㅁㅇ널" +
                ";ㅁ아니럼ㅇ;니ㅏ러") }
        
        InsightTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            value = text,
            onValueChange = { text = it },
            placeholder = "인사이트를 적어주세요",
            maxLength = 300
        )
    }
}