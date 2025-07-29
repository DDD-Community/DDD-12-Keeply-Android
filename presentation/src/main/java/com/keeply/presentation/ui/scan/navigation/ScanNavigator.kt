package com.keeply.presentation.ui.scan.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.keeply.presentation.core.navigation.HomeRoute
import com.keeply.presentation.ui.home.HomeRoute
import com.keeply.presentation.ui.scan.ScanRoute

fun NavController.navigateScan(navOptions: NavOptions) {
    navigate(HomeRoute.Scan, navOptions)
}

fun NavGraphBuilder.scanNavGraph(
    onBack: () -> Unit
) {
    composable<HomeRoute.Scan> {
        ScanRoute(onBack)
    }
}