package com.keeply.presentation.ui.folder.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.keeply.presentation.core.navigation.HomeRoute

fun NavController.navigateFolder(navOptions: NavOptions) {
    navigate(HomeRoute.Folder, navOptions)
}

fun NavGraphBuilder.folderNavGraph() {
    composable<HomeRoute.Folder> {
        val navigator = rememberFolderNavigator()

        FolderNavHost(navigator)
    }
}