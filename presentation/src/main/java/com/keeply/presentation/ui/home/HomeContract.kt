package com.keeply.presentation.ui.home

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class HomeState(
    val name: String = "Keeply",
    val tabs: ImmutableList<String> = persistentListOf("Keeply", "안칠수"),
    val selectedTabIndex: Int = 0,
    val textField: String = "",
    val textFieldMaxLength: Int = 300,
)

sealed interface HomeSideEffect {

}