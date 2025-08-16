package com.keeply.presentation.ui.my.setting.alert.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.keeply.presentation.core.navigation.AlertRoute
import com.keeply.presentation.core.navigation.HomeRoute
import com.keeply.presentation.ui.home.HomeRoute
import com.keeply.presentation.ui.my.setting.alert.SettingAlertRoute

fun NavController.navigateSettingAlert() {
    navigate(AlertRoute.AlertSetting)
}

fun NavGraphBuilder.settingAlertNavGraph() {
    composable<AlertRoute.AlertSetting> {
        SettingAlertRoute()
    }
}