package com.keeply.presentation.core.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.keeply.presentation.core.theme.KeeplyTheme
import com.keeply.presentation.core.theme.LocalTypography
import com.keeply.presentation.core.theme.neutral300
import com.keeply.presentation.core.theme.neutral400
import com.keeply.presentation.core.theme.neutral500
import com.keeply.presentation.core.theme.neutral800
import com.keeply.presentation.core.theme.neutralBlack
import com.keeply.presentation.core.theme.neutralWhite

@Composable
fun KeeplyButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    buttonSize: KeeplyButtonSize = KeeplyButtonSize.MEDIUM,
    buttonStyle: KeeplyButtonStyle = KeeplyButtonStyle.PRIMARY,
    icon: Painter? = null,
    text: String,
) {
    Surface(
        onClick = onClick,
        modifier = modifier
            .semantics { role = Role.Button },
        enabled = enabled,
        shape = RoundedCornerShape(size = 4.dp),
        color = if (enabled) buttonStyle.containerColor else buttonStyle.disabledContainerColor,
        contentColor = if (enabled) buttonStyle.contentColor else buttonStyle.disabledContentColor // 필요없으면 제거
    ) {
        ProvideTextStyle(value = LocalTypography.current.header03) {
            Row(
                Modifier
                    .defaultMinSize(
                        minWidth = buttonSize.width,
                        minHeight = buttonSize.defaultHeight
                    )
                    .padding(buttonSize.contentPadding),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (icon != null) {
                    Icon(
                        painter = icon,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = if (enabled) buttonStyle.iconTint else buttonStyle.disabledIconTint
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                }

                Text(
                    text = text
                )
            }
        }
    }
}

enum class KeeplyButtonStyle(
    val containerColor: Color,
    val contentColor: Color,
    val disabledContainerColor: Color,
    val disabledContentColor: Color,
    val iconTint: Color,
    val disabledIconTint: Color
) {
    PRIMARY(
        containerColor = neutralBlack,
        contentColor = neutralWhite,
        disabledContainerColor = neutral500,
        disabledContentColor = neutral300,
        iconTint = neutralWhite,
        disabledIconTint = neutral300
    ),
    SECONDARY(
        containerColor = Color.Transparent,
        contentColor = neutral800,
        disabledContainerColor = neutralWhite,
        disabledContentColor = neutral400,
        iconTint = neutral800,
        disabledIconTint = neutral400
    )
}

enum class KeeplyButtonSize(
    val width: Dp = 51.dp,
    val defaultHeight: Dp,
    val contentPadding: PaddingValues,
) {
    XSMALL(
        defaultHeight = 36.dp,
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
    ),
    SMALL(
        defaultHeight = 40.dp,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
    ),
    SMALL_FIXED_WIDTH(
        width = 198.dp,
        defaultHeight = 40.dp,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
    ),
    MEDIUM(
        defaultHeight = 48.dp,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
    )
}


@Preview(showBackground = true, name = "Primary - Text Only", group = "PrimaryButton")
@Composable
fun PreviewKeeplyPrimaryButtonTextOnly() {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        KeeplyButton(
            onClick = { /*TODO*/ },
            text = "Primary Button Medium",
            buttonSize = KeeplyButtonSize.MEDIUM
        )
        KeeplyButton(
            onClick = { /*TODO*/ },
            enabled = false,
            text = "Primary Buton Small (Enabled)",
            buttonSize = KeeplyButtonSize.SMALL
        )
    }
}

@Preview(showBackground = true, name = "Primary - With Icon", group = "PrimaryButton")
@Composable
fun PreviewKeeplyPrimaryButtonWithIcon() {
    KeeplyTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            KeeplyButton(
                onClick = { /*TODO*/ },
                text = "Primary Button Medium",
                icon = KeeplyTheme.icons.add,
                buttonSize = KeeplyButtonSize.MEDIUM
            )
            KeeplyButton(
                onClick = { /*TODO*/ },
                enabled = false,
                text = "Primary Buton Small (Enabled)",
                icon = KeeplyTheme.icons.add,
                buttonSize = KeeplyButtonSize.SMALL
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFE0F7FA, name = "Secondary - Text Only", group = "SecondaryButton")
@Composable
fun PreviewKeeplySecondaryButtonTextOnly() {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        KeeplyButton(
            onClick = { /*TODO*/ },
            text = "Secondary Button Medium",
            buttonSize = KeeplyButtonSize.MEDIUM,
            buttonStyle = KeeplyButtonStyle.SECONDARY
        )
        KeeplyButton(
            onClick = { /*TODO*/ },
            text = "Secondary Buton Small (Enabled)",
            enabled = false,
            buttonSize = KeeplyButtonSize.SMALL,
            buttonStyle = KeeplyButtonStyle.SECONDARY
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFE0F7FA, name = "Secondary - With Icon", group = "SecondaryButton")
@Composable
fun PreviewKeeplySecondaryButtonWithIcon() {
    KeeplyTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            KeeplyButton(
                onClick = { /*TODO*/ },
                text = "Secondary Button Medium",
                buttonSize = KeeplyButtonSize.MEDIUM,
                icon = KeeplyTheme.icons.add,
                buttonStyle = KeeplyButtonStyle.SECONDARY
            )
            KeeplyButton(
                onClick = { /*TODO*/ },
                text = "Secondary Buton Small (Enabled)",
                enabled = false,
                buttonSize = KeeplyButtonSize.SMALL,
                icon = KeeplyTheme.icons.add,
                buttonStyle = KeeplyButtonStyle.SECONDARY
            )
        }
    }
}