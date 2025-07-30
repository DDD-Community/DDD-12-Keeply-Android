package com.keeply.presentation.ui.scan.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class ScanNavigator(
    val navController: NavHostController
) {
    val startDestination = ScanRoute.ScanScreenShot
}

@Composable
fun rememberScanNavigator(
    navController: NavHostController = rememberNavController()
): ScanNavigator = remember(navController) {
    ScanNavigator(navController)
}