package com.keeply.presentation.ui.home

import androidx.compose.runtime.Immutable

@Immutable
data class HomeState(
    val name: String = "Keeply"
)

sealed interface HomeSideEffect {

}