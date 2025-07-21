package com.keeply.presentation.ui.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.keeply.presentation.core.navigation.HomeRoute
import com.keeply.presentation.ui.home.HomeRoute

fun NavController.navigateHome(navOptions: NavOptions) {
    navigate(HomeRoute.Home, navOptions)
}

fun NavGraphBuilder.homeNavGraph() {
    composable<HomeRoute.Home> {
        HomeRoute()
    }
}