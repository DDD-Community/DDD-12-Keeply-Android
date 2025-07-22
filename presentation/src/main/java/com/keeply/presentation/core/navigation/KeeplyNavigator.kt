package com.keeply.presentation.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.keeply.presentation.ui.alarm.navigation.navigateAlarm
import com.keeply.presentation.ui.folder.navigation.navigateFolder
import com.keeply.presentation.ui.home.navigation.navigateHome
import com.keeply.presentation.ui.my.navigation.navigateMy
import com.keeply.presentation.ui.scan.navigation.navigateScan

@Stable
class KeeplyNavigator(
    val navController: NavHostController
) {
    val startDestination = HomeRoute.Home

    fun popBackStack() {
        navController.popBackStack()
    }

    fun navigate(tab: KeeplyTab) {
        val navOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when (tab) {
            KeeplyTab.HOME -> navController.navigateHome(navOptions)
            KeeplyTab.FOLDER -> navController.navigateFolder(navOptions)
            KeeplyTab.SCAN -> navController.navigateScan(navOptions)
            KeeplyTab.ALARM -> navController.navigateAlarm(navOptions)
            KeeplyTab.MY -> navController.navigateMy(navOptions)
        }
    }
}

enum class KeeplyTab(
    val route: HomeRoute
) {
    HOME(
        route = HomeRoute.Home
    ),
    FOLDER(
        route = HomeRoute.Folder
    ),
    SCAN(
        route = HomeRoute.Scan
    ),
    ALARM(
        route = HomeRoute.Alarm
    ),
    MY(
        route = HomeRoute.My
    )
}

@Composable
fun rememberKeeplyNavigator(
    navController: NavHostController = rememberNavController(),
): KeeplyNavigator = remember(navController) {
    KeeplyNavigator(navController)
}