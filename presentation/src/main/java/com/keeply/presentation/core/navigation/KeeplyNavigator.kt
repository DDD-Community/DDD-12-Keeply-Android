package com.keeply.presentation.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.keeply.domain.extend.default
import com.keeply.presentation.ui.alarm.navigation.navigateAlarm
import com.keeply.presentation.ui.folder.navigation.navigateFolder
import com.keeply.presentation.ui.home.navigation.navigateHome
import com.keeply.presentation.ui.my.navigation.navigateMy
import com.keeply.presentation.ui.onboarding.navigation.navigateOnboarding
import com.keeply.presentation.ui.scan.navigation.navigateScan

@Stable
class KeeplyNavigator(
    val navController: NavHostController
) {
    val startDestination = HomeRoute.Home

    fun popBackStack() {
        navController.popBackStack()
    }

    private val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTab: KeeplyTab?
        @Composable get() {
            val destination = currentDestination ?: navController.currentDestination
            
            // FolderDetail 화면에서는 Folder 탭 활성화
            if (destination?.hasRoute<FolderRoute.FolderDetail>() == true) {
                return KeeplyTab.FOLDER
            }
            
            // 기본 탭 찾기
            return KeeplyTab.find { tab ->
                destination?.hasRoute(tab::class).default()
            }
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

    fun navigateOnboarding() = navController.navigateOnboarding()
    
    fun navigateHome() = navController.navigateHome(navOptions {
        popUpTo(navController.graph.id) {
            inclusive = true
        }
    })

    @Composable
    fun shouldShowNavigationBar()  = KeeplyTab.contains {
        navController.currentDestination?.hasRoute(it::class) == true
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
    );

    companion object {
        @Composable
        fun contains(predicate: @Composable (HomeRoute) -> Boolean): Boolean {
            return KeeplyTab.entries.map { it.route }.any { predicate(it) }
        }

        @Composable
        fun find(predicate: @Composable (HomeRoute) -> Boolean): KeeplyTab? {
            return KeeplyTab.entries.find { predicate(it.route) }
        }
    }
}

@Composable
fun rememberKeeplyNavigator(
    navController: NavHostController = rememberNavController(),
): KeeplyNavigator = remember(navController) {
    KeeplyNavigator(navController)
}