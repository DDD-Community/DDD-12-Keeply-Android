package com.keeply.presentation.ui.my.setting.alert

import androidx.compose.runtime.Immutable

@Immutable
data class SettingAlertState(
    val isShowLoading: Boolean = false,
    val allowStorageNotification: Boolean = false,
    val allowMarketingNotification: Boolean = false,
    val hasNotificationPermission: Boolean = false
)

sealed interface SettingAlertSideEffect {
    data class ShowError(val message: String) : SettingAlertSideEffect
    data object RequestNotificationPermission : SettingAlertSideEffect
    data object OpenNotificationSettings : SettingAlertSideEffect
}