package com.keeply.presentation.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.keeply.presentation.core.theme.KeeplyTheme

@Composable
fun KeeplyIconButton(
    modifier: Modifier = Modifier,
    painter: Painter,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(2.dp))
            .clickable {
                onClick()
            }
            .background(KeeplyTheme.colors.neutralBlack)
            .padding(
                vertical = 4.dp,
                horizontal = 12.dp
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painter,
            contentDescription = "버튼",
            modifier = Modifier.size(18.dp),
            tint = KeeplyTheme.colors.neutralWhite
        )
    }
}

@Preview
@Composable
fun KeeplyIconButtonPreview() {
    KeeplyIconButton(
        painter = painterResource(id = android.R.drawable.ic_menu_add),
        onClick = {}
    )
}
