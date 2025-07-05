package com.keeply.presentation.ui.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.keeply.presentation.ui.home.HomeRoute
import com.keeply.presentation.ui.naviagion.HomeRoute

fun NavGraphBuilder.homeNavGraph() {
    composable<HomeRoute.Main> {
        HomeRoute()
    }
}