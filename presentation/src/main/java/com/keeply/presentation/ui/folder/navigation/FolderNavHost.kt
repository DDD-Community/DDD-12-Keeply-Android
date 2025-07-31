package com.keeply.presentation.ui.folder.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.keeply.presentation.ui.folder.FolderRoute
import com.keeply.presentation.ui.folder.add.AddFolderRoute

@Composable
fun NavGraphBuilder.FolderNavHost(
    navigator: FolderNavigator
) {
    NavHost(
        navController = navigator.navController,
        startDestination = navigator.startDestination
    ) {

        composable<FolderRoute.Folder> {
            FolderRoute()
        }
        composable<FolderRoute.AddFolder> {
            AddFolderRoute()
        }
    }
}