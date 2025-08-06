package com.keeply.presentation.ui.folder.detail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.keeply.presentation.core.navigation.FolderRoute
import com.keeply.presentation.ui.folder.detail.FolderDetailRoute

fun NavController.navigateFolderDetail() {
    navigate(FolderRoute.AddFolder)
}

fun NavGraphBuilder.folderDetailNavGraph(
    navController: NavController,
    onNavigateBack: () -> Unit
) {
    composable<FolderRoute.FolderDetail> { backStackEntry ->
        val args = backStackEntry.arguments
        val folderId = args?.getLong("folderId") ?: 0L
        val folderName = args?.getString("folderName") ?: ""

        FolderDetailRoute(
            folderId = folderId,
            folderName = folderName,
            onBack = onNavigateBack
        )
    }
}
