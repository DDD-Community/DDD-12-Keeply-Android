package com.keeply.presentation.ui.onboarding.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.keeply.presentation.core.components.KeeplyText
import com.keeply.presentation.core.theme.KeeplyTheme
import com.keeply.presentation.ui.onboarding.OnboardingPage

@Composable
fun OnboardingPager(
    onboardingPage: OnboardingPage,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            repeat(3) { index ->
                val backgroundColor = if (index == onboardingPage.pageIndex)
                    KeeplyTheme.colors.neutral900
                else
                    KeeplyTheme.colors.neutral300

                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(6.dp)
                        .background(backgroundColor)
                )
            }
        }

        KeeplyText(
            modifier = Modifier
                .padding(top = 30.dp),
            text = stringResource(onboardingPage.title),
            style = KeeplyTheme.typography.subtitle01
        )

        KeeplyText(
            modifier = Modifier
                .padding(top = 6.dp),
            text = stringResource(onboardingPage.content),
            style = KeeplyTheme.typography.caption02,
            color = KeeplyTheme.colors.neutral700
        )

        Image(
            modifier = Modifier
                .padding(top = 30.dp)
                .fillMaxWidth(),
            painter = painterResource(onboardingPage.image),
            contentDescription = onboardingPage.name,
            contentScale = ContentScale.Crop,
            alignment = Alignment.TopCenter
        )
    }
}