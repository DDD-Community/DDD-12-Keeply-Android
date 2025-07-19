package com.keeply.presentation.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.keeply.presentation.core.theme.KeeplyTheme

@Composable
fun KeeplyAppBar(
    modifier: Modifier = Modifier,
    title: String? = null,
    leadingIcon: @Composable (() -> Unit)? = {}, // null 이면 아이콘 영역이 할당되지 않음
    trailingIcon: @Composable (() -> Unit)? = {}, // null 이면 아이콘 영역이 할당되지 않음
    trailingText: String? = null,
    customTitleContent: @Composable (BoxScope.() -> Unit)? = null,
    onClickLeading: (() -> Unit)? = null,
    onClickTrailing: (() -> Unit)? = null,
    backgroundColor: Color = KeeplyTheme.colors.neutral100,
    contentColor: Color = KeeplyTheme.colors.neutralBlack,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(backgroundColor)
            .padding(start = 16.dp, end = 16.dp, top = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        leadingIcon?.let {
            AppBarIconButton(
                content = leadingIcon,
                enabled = onClickLeading != null,
                onClick = { onClickLeading?.let { it() } }
            )
        }

        Box(
            modifier = Modifier
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            title?.let {
                KeeplyText(
                    text = it,
                    style = KeeplyTheme.typography.subtitle01,
                    color = contentColor,
                    textAlign = TextAlign.Center
                )
            }

            customTitleContent?.let { it() }
        }

        if (trailingText.isNullOrEmpty().not()) {
            KeeplyText(
                text = trailingText,
                style = KeeplyTheme.typography.subtitle01,
                color = contentColor,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .clickable(
                        enabled = onClickTrailing != null
                    ) { onClickTrailing?.let { it() } }
            )
        } else {
            trailingIcon?.let {
                AppBarIconButton(
                    content = trailingIcon,
                    enabled = onClickTrailing != null,
                    onClick = {
                        onClickTrailing?.let { it() }
                    }
                )
            }
        }

    }
}

@Composable
private fun AppBarIconButton(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
    onClick: () -> Unit,
    enabled: Boolean = true,
) {
    IconButton(
        modifier = modifier
            .size(24.dp),
        enabled = enabled,
        onClick = onClick
    ) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
private fun KeeplyAppBarPreview() {
    KeeplyTheme {
        Column {
            // Basic with back icon and title
            KeeplyAppBar(
                title = "Text",
                leadingIcon = {
                    Icon(
                        painter = KeeplyTheme.icons.chevronLeft,
                        contentDescription = "Back",
                        tint = KeeplyTheme.colors.neutralBlack
                    )
                },
                onClickLeading = {}
            )
            
            // With back icon and more icon
            KeeplyAppBar(
                title = "Text",
                leadingIcon = {
                    Icon(
                        painter = KeeplyTheme.icons.chevronLeft,
                        contentDescription = "Back",
                        tint = KeeplyTheme.colors.neutralBlack
                    )
                },
                trailingIcon = {
                    Icon(
                        painter = KeeplyTheme.icons.menuVertical,
                        contentDescription = "More",
                        tint = KeeplyTheme.colors.neutralBlack
                    )
                },
                onClickLeading = {},
                onClickTrailing = {}
            )
            
            // With back icon and text action
            KeeplyAppBar(
                title = "Text",
                leadingIcon = {
                    Icon(
                        painter = KeeplyTheme.icons.chevronLeft,
                        contentDescription = "Back",
                        tint = KeeplyTheme.colors.neutralBlack
                    )
                },
                trailingText = "Text",
                onClickLeading = {},
                onClickTrailing = {}
            )
            
            // With back icon, title and multiple actions
            KeeplyAppBar(
                title = "Text",
                leadingIcon = {
                    Icon(
                        painter = KeeplyTheme.icons.chevronLeft,
                        contentDescription = "Back",
                        tint = KeeplyTheme.colors.neutralBlack
                    )
                },
                trailingText = "Text",
                onClickLeading = {},
                onClickTrailing = {}
            )
            
            // Simple back icon and text
            KeeplyAppBar(
                customTitleContent = {
                    Row(
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .align(Alignment.CenterStart),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        KeeplyText(
                            text = "Text",
                            style = KeeplyTheme.typography.header03,
                        )

                        KeeplyText(
                            text = "Text",
                            style = KeeplyTheme.typography.header04,
                            color = KeeplyTheme.colors.neutral500
                        )
                    }
                },
                leadingIcon = {
                    Icon(
                        painter = KeeplyTheme.icons.chevronLeft,
                        contentDescription = "Back",
                        tint = KeeplyTheme.colors.neutralBlack
                    )
                },
            )
            
            // Dark theme example
            KeeplyAppBar(
                customTitleContent = {
                    KeeplyText(
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .align(Alignment.CenterStart),
                        text = "Text",
                        style = KeeplyTheme.typography.header03,
                        color = KeeplyTheme.colors.neutralWhite
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = KeeplyTheme.icons.chevronLeft,
                        contentDescription = "Back",
                        tint = KeeplyTheme.colors.neutralWhite
                    )
                },
                onClickLeading = {},
                backgroundColor = KeeplyTheme.colors.neutralBlack,
                contentColor = KeeplyTheme.colors.neutralWhite
            )
            
            // Notification style with title and bell icon
            KeeplyAppBar(
                customTitleContent = {
                    KeeplyText(
                        modifier = Modifier
                            .align(Alignment.CenterStart),
                        text = "Text",
                        style = KeeplyTheme.typography.header03
                    )
                },
                leadingIcon = null,
                trailingIcon = {
                    Icon(
                        painter = KeeplyTheme.icons.alarmStateOn,
                        contentDescription = "Notification",
                        tint = KeeplyTheme.colors.neutralBlack
                    )
                },
                onClickTrailing = {}
            )
        }
    }
}

