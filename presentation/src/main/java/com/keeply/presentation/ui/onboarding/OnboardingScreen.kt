package com.keeply.presentation.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.keeply.presentation.R
import com.keeply.presentation.core.components.KeeplyButton
import com.keeply.presentation.core.theme.KeeplyTheme
import com.keeply.presentation.ui.onboarding.component.KakaoLoginButton
import com.keeply.presentation.ui.onboarding.component.OnboardingPager
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun OnboardingRoute(
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val uiState by viewModel.collectAsState()

    OnboardingScreen(
        onboardingPage = uiState.onboardingPage,
        updateOnboardingPage = viewModel::updateOnboardingPage
    )
}

@Composable
fun OnboardingScreen(
    onboardingPage: OnboardingPage,
    updateOnboardingPage: (OnboardingPage) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(KeeplyTheme.colors.neutral100)
    ) {
        OnboardingPager(
            modifier = Modifier
                .padding(top = 40.dp)
                .width(246.dp)
                .align(Alignment.TopCenter),
            onboardingPage = onboardingPage
        )

        val bottomModifier = Modifier
            .padding(
                start = 16.dp,
                end = 16.dp,
                bottom = 50.dp
            )
            .fillMaxWidth()
            .align(Alignment.BottomCenter)

        if (onboardingPage != OnboardingPage.THIRD) {
            KeeplyButton(
                modifier = bottomModifier,
                text = stringResource(R.string.next),
                onClick = { updateOnboardingPage(onboardingPage) }
            )
        } else {
            KakaoLoginButton(
                modifier = bottomModifier
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview() {
    KeeplyTheme {
        OnboardingScreen(
            onboardingPage = OnboardingPage.FIRST
        )
    }
}