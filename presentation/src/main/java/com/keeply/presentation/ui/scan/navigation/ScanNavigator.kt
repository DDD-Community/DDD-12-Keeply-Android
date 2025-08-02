package com.keeply.presentation.ui.scan.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class ScanNavigator(
    val navController: NavHostController,
    val initialUri: String? = null
) {
    val startDestination = if (initialUri != null) {
        ScanRoute.ScanBefore(initialUri)
    } else {
        ScanRoute.ScanScreenShot
    }
}

@Composable
fun rememberScanNavigator(
    navController: NavHostController = rememberNavController(),
    initialUri: String? = null
): ScanNavigator = remember(navController, initialUri) {
    ScanNavigator(navController, initialUri)
}