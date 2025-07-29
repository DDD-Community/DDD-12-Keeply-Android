package com.keeply.presentation.ui.scan

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun ScanRoute(viewModel: ScanViewModel = hiltViewModel()) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "scan_screenshot"
    ) {
        composable("scan_screenshot") {
            ScreenshotScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onNavigateToDetail = { uri ->
                    navController.navigate("scan_before/${Uri.encode(uri.toString())}")
                }
            )
        }
        composable(
            route = "scan_before/{uri}",
            arguments = listOf(navArgument("uri") { type = NavType.StringType })
        ) { backStackEntry ->
            val uriString = backStackEntry.arguments?.getString("uri") ?: ""
            val uri = uriString.toUri()
            ScanBeforeScreen(
                viewModel = viewModel,
                uri = uri,
                onBack = { navController.popBackStack() },
                onNavigateToDetail = {}
            )
        }
    }
}