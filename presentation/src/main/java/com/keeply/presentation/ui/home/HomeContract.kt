package com.keeply.presentation.ui.home

import androidx.compose.runtime.Immutable
import com.keeply.domain.home.model.HomeData

@Immutable
data class HomeState(
    val isLoading: Boolean = false,
    val homeData: HomeData? = null,
    val isRestrictedService: Boolean = false
)

sealed interface HomeSideEffect {
    data class ShowError(val message: String) : HomeSideEffect
}