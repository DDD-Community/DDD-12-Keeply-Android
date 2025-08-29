package com.keeply.presentation.ui.my.setting.auth

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class AuthSettingViewModel @Inject constructor(
): ContainerHost<AuthSettingState, AuthSettingSideEffect>, ViewModel()  {
    override val container: Container<AuthSettingState, AuthSettingSideEffect> =
        container(AuthSettingState())

    fun updateStoragePermission(hasPermission: Boolean) = intent {
        reduce { state.copy(hasStoragePermission = hasPermission) }
    }

    fun handleStoragePermissionToggle() = intent {
        if (state.hasStoragePermission) {
            postSideEffect(AuthSettingSideEffect.OpenAppSettings)
        } else {
            postSideEffect(AuthSettingSideEffect.RequestStoragePermission)
        }
    }
}