package com.keeply.presentation.ui.alarm.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.keeply.presentation.core.navigation.HomeRoute
import com.keeply.presentation.ui.alarm.AlarmRoute
import com.keeply.presentation.ui.home.HomeRoute

fun NavController.navigateAlarm(navOptions: NavOptions) {
    navigate(HomeRoute.Alarm, navOptions)
}

fun NavGraphBuilder.alarmNavGraph() {
    composable<HomeRoute.Alarm> {
        AlarmRoute()
    }
}