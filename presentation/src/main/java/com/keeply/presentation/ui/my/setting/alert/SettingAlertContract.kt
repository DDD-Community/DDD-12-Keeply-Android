package com.keeply.presentation.ui.my.setting.alert

import androidx.compose.runtime.Immutable

@Immutable
data class SettingAlertState(
    val isShowLoading: Boolean = false
)

sealed interface SettingAlertSideEffect