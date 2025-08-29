package com.keeply.presentation.core.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.keeply.presentation.ui.alarm.navigation.alarmNavGraph
import com.keeply.presentation.ui.folder.FolderTabs
import com.keeply.presentation.ui.folder.add.navigation.addFolderNavGraph
import com.keeply.presentation.ui.folder.detail.navigation.folderDetailNavGraph
import com.keeply.presentation.ui.folder.navigation.folderNavGraph
import com.keeply.presentation.ui.home.navigation.homeNavGraph
import com.keeply.presentation.ui.my.navigation.myNavGraph
import com.keeply.presentation.ui.my.setting.alert.navigation.navigateSettingAlert
import com.keeply.presentation.ui.my.setting.alert.navigation.settingAlertNavGraph
import com.keeply.presentation.ui.my.setting.auth.navigation.authSettingNavGraph
import com.keeply.presentation.ui.my.setting.auth.navigation.navigateAuthSetting
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
        homeNavGraph(
            onClickUncategorized = {
                navigator.navigateUncategorized(FolderTabs.Uncategorized)
            },
            onClickFolder = {
                navigator.navigate(KeeplyTab.FOLDER)
            },
            onClickSelectedFolder = { folderId, folderName, folderColor ->
                navigator.navigate(KeeplyTab.FOLDER)
                navigator.navigateSelectedFolder(folderId = folderId, folderName = folderName, folderColor = folderColor)
            },
            onClickScreenshot = { uri ->
                navigator.navigateToScanWithUri(uri)
            }
        )

        folderNavGraph(
            navController = navigator.navController
        )

        scanNavGraph(
            navController = navigator.navController,
            onNavigateBack = { navigator.popBackStack() },
            onNavigateToFolder = { folderId, folderName, folderColor ->
                navigator.navigate(KeeplyTab.FOLDER)
                navigator.navigateSelectedFolder(folderId = folderId, folderName = folderName, folderColor = folderColor)
            }
        )

        alarmNavGraph()

        myNavGraph(
            navigateToAlertSetting = { navigator.navController.navigateSettingAlert() },
            navigateToAuthSetting = { navigator.navController.navigateAuthSetting() }
        )

        onboardingNavGraph(
            onEnterHome = { navigator.navigateHome() }
        )
        
        addFolderNavGraph(
            navController = navigator.navController,
            onNavigateBack = { navigator.popBackStack() }
        )
        
        folderDetailNavGraph(
            navController = navigator.navController,
            onNavigateBack = { 
                navigator.popBackStack() 
            },
            onNavigateBackWithUpdate = {
                navigator.navController.previousBackStackEntry?.savedStateHandle?.set(
                    FolderRoute.FOLDER_UPDATED, 
                    true
                )
                navigator.popBackStack()
            }
        )

        settingAlertNavGraph(
            onBackClick = { navigator.popBackStack() }
        )

        authSettingNavGraph(
            onBackClick = { navigator.popBackStack() }
        )
    }
}