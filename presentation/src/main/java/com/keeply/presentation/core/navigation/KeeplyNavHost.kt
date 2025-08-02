package com.keeply.presentation.core.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.keeply.presentation.ui.alarm.navigation.alarmNavGraph
import com.keeply.presentation.ui.folder.add.navigation.addFolderNavGraph
import com.keeply.presentation.ui.folder.add.navigation.navigateAddFolder
import com.keeply.presentation.ui.folder.navigation.folderNavGraph
import com.keeply.presentation.ui.home.navigation.homeNavGraph
import com.keeply.presentation.ui.my.navigation.myNavGraph
import com.keeply.presentation.ui.onboarding.navigation.onboardingNavGraph
import com.keeply.presentation.ui.scan.navigation.scanNavGraph

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

        folderNavGraph(
            navController = navigator.navController,
            onNavigateToAddFolder = { navigator.navController.navigateAddFolder() }
        )

        scanNavGraph()

        alarmNavGraph()

        myNavGraph()

        onboardingNavGraph(
            onEnterHome = { navigator.navigateHome() }
        )

        addFolderNavGraph(
            navController = navigator.navController,
            onNavigateBack = { navigator.popBackStack()}
        )
    }
}