package com.keeply.presentation.ui.onboarding.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.keeply.presentation.core.navigation.OnboardingRoute
import com.keeply.presentation.ui.onboarding.OnboardingRoute

fun NavController.navigateOnboarding() {
    navigate(OnboardingRoute.Onboarding)
}

fun NavGraphBuilder.onboardingNavGraph(
    onEnterHome: () -> Unit,
) {
    composable<OnboardingRoute.Onboarding> {
        OnboardingRoute(
            onEnterHome = onEnterHome
        )
    }
}