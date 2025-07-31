package com.keeply.presentation.ui.folder.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.keeply.presentation.core.navigation.HomeRoute
import com.keeply.presentation.ui.folder.FolderRoute

fun NavController.navigateFolder(navOptions: NavOptions) {
    navigate(HomeRoute.Folder, navOptions)
}

fun NavGraphBuilder.folderNavGraph(
    onNavigateToAddFolder: () -> Unit
) {
    composable<HomeRoute.Folder> {
        FolderRoute(
            onNavigateToAddFolder = onNavigateToAddFolder
        )
    }
}