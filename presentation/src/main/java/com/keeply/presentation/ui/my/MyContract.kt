package com.keeply.presentation.ui.my

import androidx.compose.runtime.Immutable

@Immutable
data class MyState(
    val isShowWithdrawModal: Boolean = false,
    val userEmail: String? = null,
    val userNickname: String? = null,
    val userImage: String? = null
)

sealed interface MySideEffect {
    data class ShowError(val message: String) : MySideEffect
    data object SuccessLogout : MySideEffect
    data object SuccessWithdraw : MySideEffect
}
