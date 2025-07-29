package com.keeply.presentation.ui.home

import androidx.compose.runtime.Immutable

@Immutable
data class HomeState(
    val isLoading: Boolean = false,
)

sealed interface HomeSideEffect