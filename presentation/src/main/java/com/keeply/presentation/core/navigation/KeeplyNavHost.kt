package com.keeply.presentation.core.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.keeply.presentation.ui.alarm.navigation.alarmNavGraph
import com.keeply.presentation.ui.folder.navigation.folderNavGraph
import com.keeply.presentation.ui.home.navigation.homeNavGraph
import com.keeply.presentation.ui.my.navigation.myNavGraph
import com.keeply.presentation.ui.scan.navigation.scanNavGraph

@Composable
internal fun KeeplyNavHost(
    navigator: KeeplyNavigator,
    onScanBack: () -> Unit
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

        folderNavGraph()

        scanNavGraph(onScanBack)

        alarmNavGraph()

        myNavGraph()
    }
}