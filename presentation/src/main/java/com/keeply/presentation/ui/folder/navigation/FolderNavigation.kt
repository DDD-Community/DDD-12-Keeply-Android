package com.keeply.presentation.ui.folder.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.keeply.domain.extend.default
import com.keeply.presentation.core.navigation.HomeRoute
import com.keeply.presentation.core.navigation.HomeRoute.Companion.FOLDER_CREATED
import com.keeply.presentation.ui.folder.FolderRoute

fun NavController.navigateFolder(navOptions: NavOptions) {
    navigate(HomeRoute.Folder, navOptions)
}

fun NavGraphBuilder.folderNavGraph(
    navController: NavController,
    onNavigateToAddFolder: () -> Unit
) {
    composable<HomeRoute.Folder> { 
        val folderCreated = navController.currentBackStackEntry?.savedStateHandle?.get<Boolean>(FOLDER_CREATED).default()
        
        FolderRoute(
            onNavigateToAddFolder = onNavigateToAddFolder,
            shouldRefresh = folderCreated
        )
    }
}