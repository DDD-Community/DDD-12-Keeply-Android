package com.keeply.presentation.ui.my.setting.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.keeply.presentation.core.navigation.AuthRoute
import com.keeply.presentation.ui.my.setting.auth.AuthSettingRoute

fun NavController.navigateAuthSetting() {
    navigate(AuthRoute.AuthSetting)
}

fun NavGraphBuilder.authSettingNavGraph(
    onBackClick: () -> Unit
) {
    composable<AuthRoute.AuthSetting> {
        AuthSettingRoute(
            onBackClick = onBackClick
        )
    }
}