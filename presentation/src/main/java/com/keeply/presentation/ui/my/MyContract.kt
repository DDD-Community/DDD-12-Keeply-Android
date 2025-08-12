package com.keeply.presentation.ui.my

import androidx.compose.runtime.Immutable

@Immutable
data class MyState(
    val isShowWithdrawModal: Boolean = false
)

sealed interface MySideEffect {
    data class ShowError(val message: String) : MySideEffect
    data object SuccessLogout : MySideEffect
    data object SuccessWithdraw : MySideEffect
}
