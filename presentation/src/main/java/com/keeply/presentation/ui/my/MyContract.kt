package com.keeply.presentation.ui.my

import androidx.compose.runtime.Immutable
import com.keeply.presentation.ui.home.HomeSideEffect

@Immutable
data class MyState(
    val isLoading: Boolean = false
)

sealed interface MySideEffect {
    data class ShowError(val message: String) : MySideEffect
    data object SuccessLogout : MySideEffect
}
