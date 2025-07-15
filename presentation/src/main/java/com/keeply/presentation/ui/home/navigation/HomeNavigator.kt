package com.keeply.presentation.ui.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.keeply.presentation.core.navigation.HomeRoute
import com.keeply.presentation.ui.home.HomeRoute

fun NavGraphBuilder.homeNavGraph() {
    composable<HomeRoute.Main> {
        HomeRoute()
    }
}