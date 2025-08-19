package com.keeply.presentation.ui.my.setting.alert

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class SettingAlertViewModel @Inject constructor(): ContainerHost<SettingAlertState, SettingAlertSideEffect>, ViewModel()  {
    override val container: Container<SettingAlertState, SettingAlertSideEffect> =
        container(SettingAlertState())
}