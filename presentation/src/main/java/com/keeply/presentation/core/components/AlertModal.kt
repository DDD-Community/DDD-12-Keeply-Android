package com.keeply.presentation.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.keeply.presentation.core.theme.KeeplyTheme

@Composable
fun BaseAlertModal(
    onDismissCallback: () -> Unit = {},
    dismissOnBackPress: Boolean = true,
    dismissOnClickOutside: Boolean = true,
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissCallback,
        properties = DialogProperties(
            dismissOnBackPress = dismissOnBackPress,
            dismissOnClickOutside = dismissOnClickOutside
        )
    ) {
        Surface(
            modifier = Modifier
                .wrapContentSize()
                .clip(RoundedCornerShape(4.dp))
                .background(KeeplyTheme.colors.neutralWhite)
        ) {
            content()
        }
    }
}

@Composable
fun KeeplyAlertModal(
    title: String,
    content: String,
    confirmButtonText: String,
    cancelButtonText: String? = null,
    confirmButtonCallback: () -> Unit = {},
    cancelButtonCallback: () -> Unit = {},
    onDismissCallback: () -> Unit = {},
    dismissOnBackPress: Boolean = true,
    dismissOnClickOutside: Boolean = true,
) {
    BaseAlertModal(
        onDismissCallback = onDismissCallback,
        dismissOnBackPress = dismissOnBackPress,
        dismissOnClickOutside = dismissOnClickOutside,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp, start = 12.dp, end = 12.dp, bottom = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            KeeplyText(
                modifier = Modifier
                    .width(200.dp)
                    .align(Alignment.CenterHorizontally),
                text = title,
                style = KeeplyTheme.typography.subtitle01,
                textAlign = TextAlign.Center
            )

            KeeplyText(
                modifier = Modifier
                    .width(200.dp)
                    .align(Alignment.CenterHorizontally),
                text = content,
                style = KeeplyTheme.typography.body,
                color = KeeplyTheme.colors.neutral800,
                textAlign = TextAlign.Center
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 28.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                cancelButtonText?.let {
                    KeeplyButton(
                        modifier = Modifier
                            .weight(1f),
                        text = it,
                        buttonStyle = KeeplyButtonStyle.SECONDARY,
                        onClick = {
                            cancelButtonCallback()
                        }
                    )
                }

                KeeplyButton(
                    modifier = Modifier
                        .weight(1f),
                    text = confirmButtonText,
                    onClick = {
                        confirmButtonCallback()
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun KeeplyAlertPreview() {
    KeeplyTheme {
        KeeplyAlertModal(
            title = "Alert Title",
            content = "This is the content of the alert.",
            confirmButtonText = "Confirm",
            cancelButtonText = "Cancel"
        )
    }
}

