package com.keeply.presentation.ui.folder.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.keeply.presentation.R
import com.keeply.presentation.core.components.ImageFrame
import com.keeply.presentation.core.components.KeeplyText
import com.keeply.presentation.core.theme.KeeplyTheme

@Composable
fun UncategorizedCardItem(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        ImageFrame(
            painter = painterResource(R.drawable.img_onboarding_03)
        )

        KeeplyText(
            modifier = Modifier
                .padding(bottom = 6.dp)
                .background(
                    color = Color(0xE5FFFFFF),
                    shape = CircleShape
                ).padding(
                    horizontal = 6.dp,
                    vertical = 2.dp
                ).align(Alignment.BottomCenter),
            text = "D-30",
            style = KeeplyTheme.typography.caption02,
            color = KeeplyTheme.colors.neutral600
        )
    }
}

@Preview
@Composable
fun UncategorizedCardItemPreview() {
    KeeplyTheme {
        UncategorizedCardItem()
    }
}