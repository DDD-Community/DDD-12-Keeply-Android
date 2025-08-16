package com.keeply.presentation.ui.my.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.keeply.presentation.core.navigation.HomeRoute
import com.keeply.presentation.ui.home.HomeRoute
import com.keeply.presentation.ui.my.MyRoute

fun NavController.navigateMy(navOptions: NavOptions) {
    navigate(HomeRoute.My, navOptions)
}

fun NavGraphBuilder.myNavGraph(
    navigateToAlertSetting: () -> Unit = {}
) {
    composable<HomeRoute.My> {
        MyRoute(
            navigateToAlertSetting = navigateToAlertSetting
        )
    }
}