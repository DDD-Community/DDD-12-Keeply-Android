package com.keeply.presentation.ui.naviagion

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Stable
class KeeplyNavigator(
    val navController: NavHostController
) {
    val startDestination = HomeRoute.Main

    fun popBackStack() {
        navController.popBackStack()
    }
}

@Composable
fun rememberKeeplyNavigator(
    navController: NavHostController = rememberNavController(),
): KeeplyNavigator = remember(navController) {
    KeeplyNavigator(navController)
}