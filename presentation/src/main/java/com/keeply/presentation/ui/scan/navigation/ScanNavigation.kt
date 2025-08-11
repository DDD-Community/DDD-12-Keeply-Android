package com.keeply.presentation.ui.scan.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.keeply.presentation.core.navigation.HomeRoute
import com.keeply.presentation.ui.folder.navigation.navigateFolder

fun NavController.navigateScan(navOptions: NavOptions) {
    navigate(HomeRoute.Scan, navOptions)
}

fun NavGraphBuilder.scanNavGraph(
    navController: NavController,
    onNavigateBack: () -> Unit
) {
    composable<HomeRoute.Scan> { backStackEntry ->
        val sharedImageUri = navController.previousBackStackEntry?.savedStateHandle?.get<String>("sharedImageUri")
        val navigator = rememberScanNavigator(initialUri = sharedImageUri)

        ScanNavHost(
            navigator = navigator,
            onNavigateBack = onNavigateBack,
            onNavigateToHome = onNavigateBack
        )
    }
}