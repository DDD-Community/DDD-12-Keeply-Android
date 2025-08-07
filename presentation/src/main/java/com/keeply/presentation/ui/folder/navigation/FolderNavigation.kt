package com.keeply.presentation.ui.folder.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.keeply.domain.extend.default
import com.keeply.presentation.core.navigation.FolderRoute
import com.keeply.presentation.core.navigation.FolderRoute.Companion.FOLDER_CREATED
import com.keeply.presentation.core.navigation.FolderRoute.Companion.FOLDER_UPDATED
import com.keeply.presentation.core.navigation.HomeRoute
import com.keeply.presentation.ui.folder.FolderRoute
import com.keeply.presentation.ui.folder.add.navigation.navigateAddFolder

fun NavController.navigateFolder(navOptions: NavOptions) {
    navigate(HomeRoute.Folder, navOptions)
}

fun NavGraphBuilder.folderNavGraph(
    navController: NavHostController
) {
    composable<HomeRoute.Folder> {
        val folderCreated = navController.currentBackStackEntry?.savedStateHandle?.get<Boolean>(FOLDER_CREATED).default()
        val folderUpdated = navController.currentBackStackEntry?.savedStateHandle?.get<Boolean>(FOLDER_UPDATED).default()
        
        FolderRoute(
            onNavigateToAddFolder = { navController.navigateAddFolder() },
            onNavigateToFolderDetail = { folderId, folderName, folderColor ->
                navController.navigate(FolderRoute.FolderDetail(folderId, folderName, folderColor))
            },
            shouldRefresh = folderCreated || folderUpdated
        )
        
        // Clear the flags after use
        if (folderCreated) {
            navController.currentBackStackEntry?.savedStateHandle?.set(FOLDER_CREATED, false)
        }
        if (folderUpdated) {
            navController.currentBackStackEntry?.savedStateHandle?.set(FOLDER_UPDATED, false)
        }
    }
}