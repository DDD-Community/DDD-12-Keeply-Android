package com.keeply.presentation.ui.onboarding

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.keeply.presentation.R

@Immutable
data class OnboardingState(
    val onboardingPage: OnboardingPage = OnboardingPage.FIRST,
    val showPermissionDialog: Boolean = false,
    val showPermissionUpgradeDialog: Boolean = false,
)

enum class OnboardingPage(
    val pageIndex: Int,
    @StringRes val title: Int,
    @StringRes val content: Int,
    @DrawableRes val image: Int
) {
    FIRST(
        pageIndex = 0,
        title = R.string.onboarding_page_first_title,
        content = R.string.onboarding_page_first_content,
        image = R.drawable.img_onboarding_01
    ),
    SECOND(
        pageIndex = 1,
        title = R.string.onboarding_page_second_title,
        content = R.string.onboarding_page_second_content,
        image = R.drawable.img_onboarding_02
    ),
    THIRD(
        pageIndex = 2,
        title = R.string.onboarding_page_third_title,
        content = R.string.onboarding_page_third_content,
        image = R.drawable.img_onboarding_03
    );

    companion object {
        fun nextPage(onboardingPage: OnboardingPage): OnboardingPage =
            OnboardingPage.entries.find { it.pageIndex == onboardingPage.pageIndex + 1 } ?: THIRD
    }
}

sealed interface OnboardingSideEffect {
    data object NavigateToHome : OnboardingSideEffect
    data object RequestPermission : OnboardingSideEffect
}