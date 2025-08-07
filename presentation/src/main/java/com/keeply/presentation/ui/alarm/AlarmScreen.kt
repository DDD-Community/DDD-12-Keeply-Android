package com.keeply.presentation.ui.alarm

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.keeply.presentation.R
import com.keeply.presentation.core.components.KeeplyButton
import com.keeply.presentation.core.components.KeeplyButtonSize
import com.keeply.presentation.core.components.KeeplyText
import com.keeply.presentation.core.theme.KeeplyTheme
import com.keeply.presentation.extend.shadow01

@Composable
fun AlarmRoute() {
    AlarmScreen()
}

@Composable
fun AlarmScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(KeeplyTheme.colors.neutral100)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                KeeplyText(
                    text = stringResource(id = R.string.alarm_coming_soon_title),
                    style = KeeplyTheme.typography.header02,
                    color = KeeplyTheme.colors.neutral500
                )

                KeeplyText(
                    modifier = Modifier
                        .padding(top = 6.dp),
                    text = stringResource(id = R.string.alarm_coming_soon_content),
                    style = KeeplyTheme.typography.body,
                    color = KeeplyTheme.colors.neutral500
                )

                Box(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .shadow01()
                        .clip(RoundedCornerShape(12.dp))
                        .size(88.dp)
                        .background(KeeplyTheme.colors.neutralWhite)
                ) {

                }

                KeeplyButton(
                    modifier = Modifier
                        .padding(top = 40.dp)
                        .width(220.dp),
                    text = stringResource(id = R.string.alarm_notification_button),
                    buttonSize = KeeplyButtonSize.SMALL,
                    onClick = {  }
                )
            }
        }
    }
}

@Preview
@Composable
fun AlarmScreenPreview() {
    KeeplyTheme {
        AlarmScreen()
    }
}