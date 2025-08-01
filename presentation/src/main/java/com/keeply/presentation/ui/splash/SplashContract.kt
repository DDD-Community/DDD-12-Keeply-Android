package com.keeply.presentation.ui.splash

import androidx.compose.runtime.Immutable

@Immutable
data class SplashState(
    val isLoading: Boolean = true,
    val isUserLoggedIn: Boolean? = null
)

sealed interface SplashSideEffect {
    data object NavigateToHome : SplashSideEffect
    data object NavigateToOnboarding : SplashSideEffect
}