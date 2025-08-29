package com.keeply.presentation.ui.my.setting.auth

import androidx.compose.runtime.Immutable

@Immutable
data class AuthSettingState(
    val isShowLoading: Boolean = false,
    val hasStoragePermission: Boolean = false,
)

sealed interface AuthSettingSideEffect {
    data object RequestStoragePermission : AuthSettingSideEffect
    data object OpenAppSettings : AuthSettingSideEffect
}