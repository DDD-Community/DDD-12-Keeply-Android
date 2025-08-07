package com.keeply.presentation.core.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.keeply.presentation.R
import com.keeply.presentation.core.theme.KeeplyTheme
import kotlinx.coroutines.delay

@Composable
fun KeeplyToast(
    message: String,
    isVisible: Boolean,
    modifier: Modifier = Modifier,
    duration: Long = 3000L,
    onDismiss: () -> Unit = {},
) {
    LaunchedEffect(isVisible) {
        if (isVisible) {
            delay(duration)
            onDismiss()
        }
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(
            initialOffsetY = { -it },
            animationSpec = tween(300)
        ) + fadeIn(animationSpec = tween(300)),
        exit = slideOutVertically(
            targetOffsetY = { -it },
            animationSpec = tween(300)
        ) + fadeOut(animationSpec = tween(300)),
        modifier = modifier
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            shadowElevation = 4.dp,
            color = KeeplyTheme.colors.neutralWhite
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = KeeplyTheme.icons.checkmarkFilled,
                    contentDescription = null,
                    tint = KeeplyTheme.colors.orange400
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    KeeplyText(
                        text = message,
                        modifier = Modifier
                            .padding(top = 1.dp),
                        style = KeeplyTheme.typography.subtitle01,
                        color = KeeplyTheme.colors.neutralBlack,
                    )

                    KeeplyText(
                        text = message,
                        style = KeeplyTheme.typography.body,
                        color = KeeplyTheme.colors.neutral500,
                    )
                }

            }
        }
    }
}

@Preview
@Composable
fun KeeplyToastInfoPreview() {
    KeeplyTheme {
        KeeplyToast(
            message = "정보를 확인해주세요.",
            isVisible = true
        )
    }
}