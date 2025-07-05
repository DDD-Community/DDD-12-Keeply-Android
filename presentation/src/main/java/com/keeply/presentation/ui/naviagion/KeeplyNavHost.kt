package com.keeply.presentation.ui.naviagion

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.keeply.presentation.ui.home.navigation.homeNavGraph

@Composable
internal fun KeeplyNavHost(
    navigator: KeeplyNavigator
) {
    NavHost(
        navController = navigator.navController,
        startDestination = navigator.startDestination,
        enterTransition = {
            EnterTransition.None
        },
        exitTransition = {
            ExitTransition.None
        }
    ) {
        homeNavGraph()
    }
}